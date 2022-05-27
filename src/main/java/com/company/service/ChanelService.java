package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.ChanelDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ChanelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChanelStatus;
import com.company.enums.ProfileRole;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ChanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChanelService {
    @Autowired
    private ChanelRepository chanelRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileService profileService;

    public ChanelDTO create(ChanelDTO dto, Integer pId) {
        AttachEntity photo = attachService.get(dto.getPhoto());
        AttachEntity banner = attachService.get(dto.getBanner());
        ProfileEntity profile = profileService.get(pId);
        ChanelEntity entity = new ChanelEntity();
        entity.setName(dto.getName());
        entity.setStatus(ChanelStatus.valueOf(dto.getStatus()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPhoto(photo);
        entity.setBanner(banner);
        entity.setProfile(profile);
        entity.setDescription(dto.getDescription());

        String key = UUID.randomUUID().toString();
        entity.setKey(key);

        chanelRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfile(entity.getProfile().getId());
        dto.setKey(entity.getKey());

        return dto;
    }

    public ChanelDTO update(Integer id, ChanelDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        AttachEntity photo = attachService.get(dto.getPhoto());
        AttachEntity banner = attachService.get(dto.getBanner());
        ChanelEntity entity = get(id);
        if (entity.getProfile().equals(profile) || profile.getRole().equals(ProfileRole.ADMIN)) {
            entity.setName(dto.getName());
            entity.setStatus(ChanelStatus.valueOf(dto.getStatus()));
            entity.setCreatedDate(LocalDateTime.now());
            entity.setPhoto(photo);
            entity.setBanner(banner);
            entity.setDescription(dto.getDescription());

            chanelRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setProfile(entity.getProfile().getId());
            dto.setKey(entity.getKey());

            return dto;
        }

        throw new ItemNotFoundException("Chanel not found");
    }

    public ChanelDTO updateChanelPhoto(Integer id, ChanelDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        AttachEntity photo = attachService.get(dto.getPhoto());
        ChanelEntity entity = get(id);
        if (entity.getProfile().getId().equals(pId) || profile.getRole().equals(ProfileRole.ADMIN)) {
            entity.setPhoto(photo);

            chanelRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());

            return dto;
        }
        throw new ItemNotFoundException("Chanel not found");
    }

    public ChanelDTO updateChanelBanner(Integer id, ChanelDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        AttachEntity banner = attachService.get(dto.getBanner());
        ChanelEntity entity = get(id);
        if (!entity.getProfile().getId().equals(pId) || !profile.getRole().equals(ProfileRole.ADMIN)) {
            entity.setBanner(banner);

            chanelRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());

            return dto;
        }
        throw new ItemNotFoundException("Chanel not found");
    }

    public List<ChanelDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ChanelDTO> dtoList = new ArrayList<>();
        chanelRepository.findAll(pageable).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return dtoList;
    }

    public ChanelDTO updateChanelStatus(Integer id, ChanelDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        ChanelEntity entity = get(id);
        if (entity.getProfile().getId().equals(pId) || profile.getRole().equals(ProfileRole.ADMIN)) {
            entity.setStatus(ChanelStatus.valueOf(dto.getStatus()));

            chanelRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());

            return dto;
        }
        throw new ItemNotFoundException("Chanel not found");
    }

    public List<ChanelEntity> getAllChanelUser(Integer pId) {
        List<ChanelEntity> chanelList = chanelRepository.findAll();
        List<ChanelEntity> chanelEntityList = new LinkedList<>();

        for (ChanelEntity entity : chanelList) {
            if (entity.getProfile().getId().equals(pId)) {
                chanelEntityList.add(entity);
            }
        }
        return chanelEntityList;
    }

    public ChanelEntity get(Integer id) {
        return chanelRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Chanel not found");
        });
    }

    public ChanelDTO toDTO(ChanelEntity entity) {
        ChanelDTO dto = new ChanelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus().name());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhoto(entity.getPhoto().getId());
        dto.setBanner(entity.getBanner().getId());
        dto.setProfile(entity.getProfile().getId());
        dto.setDescription(entity.getDescription());
        dto.setKey(entity.getKey());

        return dto;
    }

    public ChanelDTO toDTOShort(ChanelEntity chanel) {
        ChanelDTO dto = new ChanelDTO();
        dto.setId(chanel.getId());
        dto.setName(chanel.getName());
        return dto;
    }

    public ChanelDTO channelShorInfoWithProfile(ChanelEntity entity) {
        ChanelDTO dto = new ChanelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        if (Optional.ofNullable(entity.getPhoto()).isPresent()) {
            dto.setPhoto(entity.getPhoto().getId());
        }

        ProfileDTO profileDTO = profileService.toShortDTO(entity.getProfile());
        dto.setProfileDTO(profileDTO);
        return dto;
    }
}
