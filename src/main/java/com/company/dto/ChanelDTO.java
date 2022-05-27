package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChanelDTO {
    private Integer id;
    private String name;
    private String photo;
    private String description;
    private String status;
    private String banner;
    private Integer profile;
    private ProfileDTO profileDTO;
    private String key;
    private LocalDateTime createdDate;
}
