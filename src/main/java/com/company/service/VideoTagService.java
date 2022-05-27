package com.company.service;

import com.company.dto.TagDTO;
import com.company.dto.VideoTagDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.TagEntity;
import com.company.entity.VideoEntity;
import com.company.entity.VideoTagEntity;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.VideoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class VideoTagService {
    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ProfileService profileService;

    public VideoTagDTO create(VideoTagDTO dto, Integer pId) {
        VideoEntity video = videoService.get(dto.getVideoId());
        TagEntity tag = tagService.get(dto.getTagId());
        VideoTagEntity entity = new VideoTagEntity();

        if (profileService.get(pId).getId().equals(video.getProfileId())) {
            entity.setVideoId(video.getId());
            entity.setTagId(tag.getId());
            entity.setCreatedDate(LocalDateTime.now());

            videoTagRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());

            return dto;
        }

        throw new AppForbiddenException("Not Forbidden");
    }

    public boolean delete(Integer id , Integer pId) {
        VideoTagEntity entity = get(id);

        if (profileService.get(pId).getId().equals(entity.getVideo().getProfileId())) {
            videoTagRepository.delete(entity);
            return true;
        }
        return false;
    }

    public List<VideoTagDTO> getVideoTagListBuVideoId() {
        List<VideoTagEntity> videoEntityList = videoTagRepository.findAll();
        List<VideoTagDTO> videoTagDTOList = new LinkedList<>();

        for (VideoTagEntity entity : videoEntityList) {
            VideoTagDTO dto = new VideoTagDTO();
            dto.setId(entity.getId());
            dto.setTag(tagService.toDTO(tagService.get(entity.getTagId())));
            dto.setVideoId(entity.getVideoId());
            dto.setCreatedDate(entity.getCreatedDate());
            videoTagDTOList.add(dto);
        }

        return videoTagDTOList;
    }

    public VideoTagEntity get(Integer id) {
        return videoTagRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }
}
