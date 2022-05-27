package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistVideoDTO {
    private Integer id;
    private LocalDateTime created_date;
    private Integer order_num;

    private Integer playlistId;
    private Integer videoId;
    private Integer chanelId;


    private PlaylistDTO playlist;
    private VideoDTO video;
    private ChanelDTO chanel;
}
