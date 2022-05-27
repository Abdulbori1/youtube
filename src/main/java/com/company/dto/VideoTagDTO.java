package com.company.dto;

import com.company.entity.TagEntity;
import com.company.entity.VideoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoTagDTO {
    private Integer id;
    private LocalDateTime createdDate;

    private Integer tagId;
    private Integer videoId;

    private VideoDTO video;
    private TagDTO tag;
}
