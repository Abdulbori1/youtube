package com.company.dto;

import com.company.enums.EmailType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDTO {
    private Integer id;
    private String toEmail;
    private EmailType type;
    private LocalDateTime sendDate = LocalDateTime.now();
}
