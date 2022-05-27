package com.company.dto;

import com.company.entity.AttachEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private AttachDTO photo;
    private String password;
    private String role;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private String status;

    private String jwt;

    private String photoId;

    private AttachDTO image;
}
