package com.company.dto;

import com.company.entity.ChanelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.NotificationType;
import com.company.enums.SubscribeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDTO {

    private Integer id;
    private LocalDateTime createdDate;
    private String status;
    private String notificationType;

    private Integer profileId;
    private Integer chanelId;

    private ProfileDTO profile;
    private ChanelDTO chanel;
}
