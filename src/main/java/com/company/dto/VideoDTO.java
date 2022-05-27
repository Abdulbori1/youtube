package com.company.dto;

import com.company.entity.TagEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {
    private Integer id;
    private String title;
    private LocalDateTime created_date;
    private LocalDateTime published_date;
    private String status;
    private String key;
    private String type;
    private Integer view_count;
    private String description;
    private Integer likeCount;
    private Integer dislikeCount;
    private Boolean isUserLiked;
    private Boolean isUserDisliked;

    private Integer profileId;
    private Integer categoryId;
    private String attachId;
    private String previewAttachId;
    private Integer chanelId;

    private AttachDTO previewAttach;
    private AttachDTO attach;
    private CategoryDTO category;
    private ChanelDTO chanel;
    private ProfileDTO profile;

    private Integer duration;
    private List<TagDTO> tagList;
}
