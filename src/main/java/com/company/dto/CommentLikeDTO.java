package com.company.dto;

import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.CommentLikeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private String type;

    private Integer profileId;
    private Integer commentId;

    private ProfileDTO profile;
    private CommentDTO comment;
}
