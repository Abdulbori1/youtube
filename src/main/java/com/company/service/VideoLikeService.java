package com.company.service;

import com.company.dto.VideoLikeDTO;
import com.company.entity.VideoEntity;
import com.company.entity.VideoLikeEntity;
import com.company.enums.VideoLikeType;
import com.company.exception.ItemNotFoundException;
import com.company.repository.VideoLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;
    @Autowired
    private VideoService videoService;

    public VideoLikeDTO create(VideoLikeDTO dto, Integer pId) {
        VideoEntity video = videoService.get(dto.getVideoId());
        VideoLikeEntity entity = new VideoLikeEntity();
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfileId(pId);
        entity.setVideoId(video.getId());
        entity.setType(VideoLikeType.valueOf(dto.getType()));

        videoLikeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfileId());

        return dto;
    }

    public boolean remove(Integer id, Integer pId) {
        VideoLikeEntity entity = get(id);
        if (entity.getProfileId().equals(pId)) {
            videoLikeRepository.delete(entity);
            return true;
        }
        return false;
    }

    private VideoLikeEntity get(Integer id) {
        return videoLikeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }

    public List<VideoLikeDTO> commentList(Integer pId) {

        List<VideoLikeEntity> commentList = videoLikeRepository.findAll();
        List<VideoLikeDTO> commentEntities = new LinkedList<>();

        for (VideoLikeEntity entity : commentList) {
            if (entity.getProfileId().equals(pId)) {
                commentEntities.add(toDTO(entity));
            }
        }
        return commentEntities;
    }

    public List<VideoLikeDTO> commentListAdmin(Integer pId) {

        List<VideoLikeEntity> commentList = videoLikeRepository.findAll();
        List<VideoLikeDTO> commentEntities = new LinkedList<>();

        for (VideoLikeEntity entity : commentList) {
            if (entity.getProfileId().equals(pId)) {
                commentEntities.add(toDTO(entity));
            }
        }
        return commentEntities;
    }

    private VideoLikeDTO toDTO(VideoLikeEntity entity) {
        VideoLikeDTO dto = new VideoLikeDTO();
        dto.setId(entity.getId());
        dto.setVideo(videoService.toDTOShort(videoService.get(entity.getVideoId())));

        return dto;
    }

    public Integer likeCount(Integer videoId) {
        return videoLikeRepository.countByVideoIdAndType(videoId, VideoLikeType.LIKE);
    }

    public Integer dislikeCount(Integer videoId) {
        return videoLikeRepository.countByVideoIdAndType(videoId, VideoLikeType.DISLIKE);
    }

    public Boolean isUserLikes(Integer id, Integer profileId) {
        if (videoLikeRepository.findByVideoIdAndProfileId(id, profileId).getType().equals(VideoLikeType.LIKE)) {
            return true;
        }
        return false;
    }

    public Boolean isUserDisliked(Integer id, Integer profileId) {
        if (videoLikeRepository.findByVideoIdAndProfileId(id, profileId).getType().equals(VideoLikeType.DISLIKE)) {
            return true;
        }
        return false;
    }
}
