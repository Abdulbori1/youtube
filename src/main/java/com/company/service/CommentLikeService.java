package com.company.service;

import com.company.dto.CommentLikeDTO;
import com.company.entity.CommentEntity;
import com.company.entity.CommentLikeEntity;
import com.company.entity.PlaylistEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.CommentLikeType;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CommentService commentService;

    public CommentLikeDTO create(CommentLikeDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        CommentEntity comment = commentService.get(dto.getCommentId());

        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setProfileId(profile.getId());
        entity.setCommentId(comment.getId());
        entity.setType(CommentLikeType.valueOf(dto.getType()));
        entity.setCreatedDate(LocalDateTime.now());

        commentLikeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfileId());

        return dto;
    }

    public boolean remove(Integer id, Integer pId) {
        CommentLikeEntity entity = get(id);
        if (entity.getProfileId().equals(pId)) {
            commentLikeRepository.save(entity);
            return true;
        }
        return false;
    }

    public List<CommentLikeDTO> userLikedCommentList(Integer pId) {
        List<CommentLikeEntity> commentLikeList = commentLikeRepository.findByProfileIdOrderByCreatedDateDesc(pId);
        List<CommentLikeDTO> commentLikeDTOList = new LinkedList<>();

        for (CommentLikeEntity entity : commentLikeList) {
            commentLikeDTOList.add(toDTO(entity));
        }
        return commentLikeDTOList;
    }

    public List<CommentLikeDTO> userLikedCommentListAdm(Integer id) {
        List<CommentLikeEntity> commentLikeList = commentLikeRepository.findByProfileIdOrderByCreatedDateDesc(id);
        List<CommentLikeDTO> commentLikeDTOList = new LinkedList<>();

        for (CommentLikeEntity entity : commentLikeList) {
            commentLikeDTOList.add(toDTO(entity));
        }
        return commentLikeDTOList;
    }

    public CommentLikeEntity get(Integer id) {
        return commentLikeRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Not found!");
                });
    }

    public CommentLikeDTO toDTO(CommentLikeEntity entity) {
        CommentLikeDTO dto = new CommentLikeDTO();
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setCommentId(entity.getCommentId());
        dto.setType(entity.getType().name());
        dto.setCreatedDate(LocalDateTime.now());

        return dto;
    }
}
