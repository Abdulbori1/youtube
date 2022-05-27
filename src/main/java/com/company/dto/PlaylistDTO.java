package com.company.dto;

import com.company.entity.ChanelEntity;
import com.company.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private Integer order_num;
    private LocalDateTime createdDate;
    private Integer videoCount;
    private List<VideoDTO> videoList;

    private ChanelDTO chanel;
    private Integer channelId;

    private ProfileDTO profile;
    private Integer profileId;

}
