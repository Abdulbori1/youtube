package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.EmailAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private EmailService emailService;

    public ProfileDTO create(ProfileDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }
        ProfileEntity entity = new ProfileEntity();
        if (dto.getPhotoId() != null) {
            AttachEntity attachEntity = attachService.get(dto.getPhotoId());
            entity.setPhoto(attachEntity);
        }
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);

        entity.setName(dto.getName());
        entity.setRole(ProfileRole.valueOf(dto.getRole()));
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setStatus(ProfileStatus.valueOf(dto.getStatus()));

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<ProfileEntity> list() {
        return profileRepository.findAll();
    }

    public ProfileDTO getById(Integer id) {
        ProfileEntity entity = profileRepository.getById(id);
        return toDTO(entity);
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus().name());
        if (entity.getPhoto() != null) {
            dto.setPhotoId(entity.getPhoto().getId());
        }

        return dto;
    }

    public ProfileDTO update(Integer id, ProfileDTO dto) {
        ProfileEntity entity = profileRepository.getById(id);
        if (dto.getPhotoId() != null) {
            AttachEntity attachEntity = attachService.get(dto.getPhotoId());
            entity.setPhoto(attachEntity);
        }
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setStatus(ProfileStatus.valueOf(dto.getStatus()));

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public boolean delete(Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (entity == null) {
            return false;
        }

        profileRepository.delete(entity);
        return true;
    }

    public void updateEmail(ProfileDTO dto, Integer pId) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }

        ProfileEntity entity = get(pId);

        String pswd = DigestUtils.md5Hex(dto.getPassword());
        Optional<ProfileEntity> profileEntity =
                profileRepository.findByEmailAndPassword(dto.getEmail(), pswd);
        entity.setEmail(dto.getEmail());

        entity.setStatus(ProfileStatus.BLOCK);
        profileRepository.save(entity);

        Thread thread = new Thread() {
            @Override
            public void run() {
                sendVerificationEmail(entity);
            }
        };
        thread.start();
    }

    public void updatePassword(ProfileDTO dto, Integer pId) {
        ProfileEntity entity = get(pId);

        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);
        entity.setEmail(entity.getEmail());
        entity.setStatus(ProfileStatus.BLOCK);
        profileRepository.save(entity);

        Thread thread = new Thread() {
            @Override
            public void run() {
                sendVerificationEmail(entity);
            }
        };
        thread.start();
    }

    private void sendVerificationEmail(ProfileEntity entity) {
        StringBuilder builder = new StringBuilder();
        String jwt = JwtUtil.encode(entity.getId());
        builder.append("Salom bormsin \n");
        builder.append("To verify your registration click to next link.");
        builder.append("http://localhost:8080/auth/verification/").append(jwt);
        builder.append("\nMazgi!");
        emailService.send(entity.getEmail(), "Activate Your Registration", builder.toString());
    }

    public void updateProfileDetail(ProfileDTO dto, Integer pId) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }

        ProfileEntity entity = get(pId);

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());

        profileRepository.save(entity);
    }

    public void updateProfileAttach(ProfileDTO dto, Integer pId) {
        AttachEntity attachEntity = attachService.get(dto.getPhotoId());
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }

        ProfileEntity entity = get(pId);
        entity.setPhoto(attachEntity);

        profileRepository.save(entity);
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }

    public ProfileDTO toShortDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());

        if (Optional.ofNullable(entity.getPhoto()).isPresent()) {
            dto.setPhotoId(entity.getPhoto().getId());
        }

        return dto;
    }

    public ProfileDTO toDTOReport(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());

        if (Optional.ofNullable(entity.getPhoto()).isPresent()) {
            dto.setPhoto(attachService.toDTOReport(entity.getPhoto()));
        }

        return dto;
    }
}
