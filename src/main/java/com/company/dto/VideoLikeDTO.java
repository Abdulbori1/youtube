package com.company.dto;

import com.company.enums.VideoLikeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoLikeDTO {

    private Integer id;
    private LocalDateTime createdDate;
    private String type;

    private Integer profileId;
    private Integer videoId;

    private ProfileDTO profile;
    private VideoDTO video;
}
