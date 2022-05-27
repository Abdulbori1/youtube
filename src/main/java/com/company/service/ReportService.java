package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.ReportDTO;
import com.company.entity.ChanelEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.ReportEntity;
import com.company.entity.VideoEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ReportType;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ChanelService chanelService;
    @Autowired
    private VideoService videoService;

    public ReportDTO create(ReportDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);

        ReportEntity entity = new ReportEntity();
        entity.setProfileId(profile.getId());
        entity.setContent(dto.getContent());
        entity.setType(ReportType.valueOf(dto.getType()));
        entity.setCreatedDate(LocalDateTime.now());

        if (entity.getType().equals(ReportType.CHANEL)) {
            ChanelEntity chanel = chanelService.get(dto.getEntity_id());
            entity.setEntity_id(chanel.getId());
        } else if (entity.getType().equals(ReportType.VIDEO)) {
            VideoEntity video = videoService.get(dto.getEntity_id());
            entity.setEntity_id(video.getId());
        }

        reportRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<ReportDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ReportDTO> dtoList = new ArrayList<>();
        reportRepository.findAll(pageable).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return dtoList;
    }

    public Boolean remove(Integer id, Integer pId) {
        ProfileEntity profile = profileService.get(pId);

        ReportEntity entity = get(id);
        if (profile.getRole().equals(ProfileRole.ADMIN)) {
            reportRepository.delete(entity);
            return true;
        }
        return false;
    }

    public List<ReportDTO> reportListByUserId (Integer id) {

        List<ReportEntity> entityList = reportRepository.findAll();
        List<ReportDTO> dtoList = new LinkedList<>();
        for (ReportEntity entity : entityList) {
            if (entity.getProfileId().equals(id)) {
                dtoList.add(toDTO(entity));
            }
        }
        return dtoList;
    }

    private ReportEntity get(Integer id) {
        return reportRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
    }

    private ReportDTO toDTO(ReportEntity entity) {
        ReportDTO dto = new ReportDTO();
        dto.setId(entity.getId());
        dto.setProfile(profileService.toDTOReport(entity.getProfile()));
        dto.setContent(entity.getContent());
        dto.setEntity_id(entity.getEntity_id());
        dto.setType(entity.getType().name());

        return dto;
    }
}
