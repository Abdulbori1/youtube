package com.company.service;

import com.company.dto.CommentDTO;
import com.company.entity.CommentEntity;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CommentRepository;
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
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private VideoService videoService;

    public CommentDTO create(CommentDTO dto, Integer pId) {
        CommentEntity entity = new CommentEntity();
        if (dto.getReplayId() != null) {
            CommentEntity comment = get(dto.getReplayId());
            entity.setReplayId(dto.getReplayId());
        }
        entity.setVideoId(dto.getVideoId());
        entity.setContent(dto.getContent());
        entity.setProfileId(pId);
        entity.setCreatedDate(LocalDateTime.now());

        commentRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CommentDTO update(Integer id, CommentDTO dto, Integer pId) {

        CommentEntity entity = get(id);
        if (entity.getProfileId().equals(pId)) {
            entity.setVideoId(dto.getVideoId());
            entity.setContent(dto.getContent());
            entity.setReplayId(dto.getReplayId());

            commentRepository.save(entity);
            dto.setId(entity.getId());
            dto.setProfileId(entity.getProfileId());

            return dto;
        }

        throw new AppForbiddenException("Forbidden");
    }

    public boolean delete(Integer id, Integer pId) {

        CommentEntity entity = get(id);
        if (entity.getProfileId().equals(pId) || profileService.get(pId).getRole().equals(ProfileRole.ADMIN)) {
            commentRepository.delete(entity);
            return true;
        }

        return false;
    }

    public List<CommentDTO> paginationList(int page, int size, Integer pId) {
        if (profileService.get(pId).getRole().equals(ProfileRole.ADMIN)) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

            List<CommentDTO> dtoList = new ArrayList<>();
            commentRepository.findAll(pageable).stream().forEach(entity -> {
                dtoList.add(toDTO(entity));
            });

            return dtoList;
        }

        throw new AppForbiddenException("Forbidden");
    }

    public List<CommentDTO> commentList(Integer pId) {

        List<CommentEntity> commentList = commentRepository.findAll();
        List<CommentDTO> commentEntities = new LinkedList<>();

        for (CommentEntity entity : commentList) {
            if (entity.getProfileId().equals(pId)) {
                commentEntities.add(toDTO(entity));
            }
        }
        return commentEntities;
    }

    public List<CommentDTO> commentListByVideo(Integer videoId) {

        List<CommentEntity> commentList = commentRepository.findAll();
        List<CommentDTO> commentEntities = new LinkedList<>();

        for (CommentEntity entity : commentList) {
            if (entity.getVideoId().equals(videoId)) {
                commentEntities.add(toDTOGet(entity));
            }
        }
        return commentEntities;
    }

    public List<CommentDTO> commentListByProfile(Integer pId) {
        List<CommentEntity> commentList = commentRepository.findByProfileId(pId);
        List<CommentDTO> commentEntities = new LinkedList<>();

        for (CommentEntity entity : commentList) {
            commentEntities.add(toDTO(entity));
        }
        return commentEntities;
    }

    public List<CommentDTO> commentListByReply(Integer commentId) {

        List<CommentEntity> commentList = commentRepository.findAll();
        List<CommentDTO> commentEntities = new LinkedList<>();

        for (CommentEntity entity : commentList) {
            if (entity.getReplayId() != null) {
                if (entity.getReplayId().equals(commentId)) {
                    commentEntities.add(toDTOGet(entity));
                }
            }
        }
        return commentEntities;
    }

    private CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLikeCount(entity.getLikeCount());
        dto.setDislikeCount(entity.getDislikeCount());
        dto.setVideo(videoService.toDTOShort(entity.getVideo()));

        return dto;
    }

    private CommentDTO toDTOGet(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLikeCount(entity.getLikeCount());
        dto.setDislikeCount(entity.getDislikeCount());
        dto.setProfile(videoService.toDTOShort(entity.getVideo()).getProfile());

        return dto;
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Comment not found");
        });
    }
}
