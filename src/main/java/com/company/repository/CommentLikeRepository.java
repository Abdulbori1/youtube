package com.company.repository;

import com.company.dto.CommentLikeDTO;
import com.company.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Integer> {
    List<CommentLikeEntity> findByProfileIdOrderByCreatedDateDesc(Integer pId);
}
