package com.company.service;

import com.company.dto.SubscriptionDTO;
import com.company.entity.SubscriptionEntity;
import com.company.enums.NotificationType;
import com.company.enums.SubscribeStatus;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ChanelService chanelService;

    public SubscriptionDTO create(SubscriptionDTO dto, Integer pId) {
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setChanelId(dto.getChanelId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfileId(pId);
        entity.setStatus(SubscribeStatus.valueOf(dto.getStatus()));
        entity.setNotificationType(NotificationType.valueOf(dto.getNotificationType()));

        subscriptionRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfileId());
        dto.setId(entity.getId());

        return dto;
    }

    public SubscriptionDTO updateStatus(Integer id, SubscriptionDTO dto, Integer pId) {
        SubscriptionEntity entity = get(id);
        if (entity.getProfileId().equals(pId)) {
            entity.setStatus(SubscribeStatus.valueOf(dto.getStatus()));

            subscriptionRepository.save(entity);
            dto.setId(entity.getId());

            return dto;
        }

        throw new AppForbiddenException("Forbidden");
    }

    public SubscriptionDTO updateNotification(Integer id, SubscriptionDTO dto, Integer pId) {
        SubscriptionEntity entity = get(id);
        if (entity.getProfileId().equals(pId)) {
            entity.setNotificationType(NotificationType.valueOf(dto.getNotificationType()));

            subscriptionRepository.save(entity);
            dto.setId(entity.getId());

            return dto;
        }

        throw new AppForbiddenException("Forbidden");
    }

    public List<SubscriptionDTO> commentList(Integer pId) {
        List<SubscriptionEntity> subscriptionList = subscriptionRepository.findAll();
        List<SubscriptionDTO> subscriptionDTOList = new LinkedList<>();

        for (SubscriptionEntity entity : subscriptionList) {
            if (entity.getProfileId().equals(pId)) {
                subscriptionDTOList.add(toDTO(entity));
            }
        }
        return subscriptionDTOList;
    }

    public List<SubscriptionDTO> commentListAdmin(Integer pId) {
        List<SubscriptionEntity> subscriptionList = subscriptionRepository.findAll();
        List<SubscriptionDTO> subscriptionDTOList = new LinkedList<>();

        for (SubscriptionEntity entity : subscriptionList) {
            if (entity.getProfileId().equals(pId)) {
                subscriptionDTOList.add(toDTOAdmin(entity));
            }
        }
        return subscriptionDTOList;
    }

    private SubscriptionDTO toDTOAdmin(SubscriptionEntity entity) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(entity.getId());
        dto.setChanel(chanelService.toDTO(entity.getChanel()));
        dto.setNotificationType(entity.getNotificationType().name());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    private SubscriptionDTO toDTO(SubscriptionEntity entity) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(entity.getId());
        dto.setChanel(chanelService.toDTO(entity.getChanel()));
        dto.setNotificationType(entity.getNotificationType().name());

        return dto;
    }

    public SubscriptionEntity get(Integer id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }
}
