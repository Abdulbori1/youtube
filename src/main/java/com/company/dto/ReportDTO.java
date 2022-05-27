package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO {
    private Integer id;
    private String content;
    private Integer profileId;
    private Integer entity_id;
    private LocalDateTime createdDate;

    private String type;
    private ProfileDTO profile;
}
