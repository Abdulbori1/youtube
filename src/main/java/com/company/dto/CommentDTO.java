package com.company.dto;

import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.VideoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    private String content;
    private Integer likeCount;
    private Integer dislikeCount;
    private LocalDateTime createdDate;

    private ProfileDTO profile;
    private VideoDTO video;

    private Integer videoId;
    private Integer profileId;
    private Integer replayId;
}
