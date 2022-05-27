package com.company.service;

import com.company.dto.ChanelDTO;
import com.company.dto.PlaylistDTO;
import com.company.dto.VideoDTO;
import com.company.entity.ChanelEntity;
import com.company.entity.PlaylistEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.PlaylistStatus;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.PlaylistRepository;
import com.company.repository.PlaylistVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ChanelService chanelService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;

    public PlaylistDTO create(PlaylistDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        ChanelEntity chanel = chanelService.get(dto.getChannelId());
        PlaylistEntity entity = new PlaylistEntity();
        entity.setOrder_num(dto.getOrder_num());
        entity.setStatus(PlaylistStatus.valueOf(dto.getStatus()));
        entity.setDescription(dto.getDescription());
        entity.setChanelId(chanel.getId());
        entity.setName(dto.getName());
        entity.setUserId(profile.getId());
        entity.setCreatedDate(LocalDateTime.now());

        playlistRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getUserId());
        dto.setVideoCount(0);

        return dto;
    }

    public PlaylistDTO update(Integer id, PlaylistDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        ChanelEntity chanel = chanelService.get(dto.getChannelId());
        PlaylistEntity entity = getEntity(id);
        if (entity.getUser().equals(profile) || profile.getRole().equals(ProfileRole.ADMIN)) {
            entity.setOrder_num(dto.getOrder_num());
            entity.setStatus(PlaylistStatus.valueOf(dto.getStatus()));
            entity.setDescription(dto.getDescription());
            entity.setChanelId(chanel.getId());
            entity.setName(dto.getName());
            entity.setCreatedDate(LocalDateTime.now());

            playlistRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setProfileId(entity.getUser().getId());
            dto.setVideoCount(playlistVideoRepository.getVideoCountByPlaylistId(entity.getId()));

            return dto;
        }
        throw new ItemNotFoundException("Playlist not found");
    }

    public PlaylistDTO updatePlaylistStatus(Integer id, PlaylistDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        PlaylistEntity entity = getEntity(id);
        if (entity.getUser().equals(profile) || profile.getRole().equals(ProfileRole.ADMIN)) {
            entity.setStatus(PlaylistStatus.valueOf(dto.getStatus()));

            playlistRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());

            return dto;
        }
        throw new ItemNotFoundException("Playlist not found");
    }

    public boolean deletePlaylist(Integer id, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        PlaylistEntity entity = getEntity(id);
        if (entity.getUser().equals(profile) || profile.getRole().equals(ProfileRole.ADMIN)) {
            playlistRepository.delete(entity);
            return true;
        }

        return false;
    }

    public List<PlaylistDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<PlaylistDTO> dtoList = new ArrayList<>();
        playlistRepository.findAll(pageable).stream().forEach(entity -> {
            dtoList.add(toFullDTO(entity));
        });

        return dtoList;
    }

    public PlaylistEntity getEntity(Integer id) {
        return playlistRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Playlist not found");
        });
    }

    public List<PlaylistDTO> getAllChanelUserAdm(Integer id) {
        List<PlaylistDTO> dtoList = new ArrayList<>();
        playlistRepository.findAll().forEach(entity -> {
            if (entity.getUserId().equals(id)) {
                dtoList.add(toShortDTO(entity));
            }
        });

        return dtoList;
    }

    public List<PlaylistDTO> getAllChanelUser(Integer pId) {
        List<PlaylistEntity> playlistList = playlistRepository.findAll();
        List<PlaylistDTO> playlistEntities = new LinkedList<>();

        for (PlaylistEntity entity : playlistList) {
            if (entity.getUser().getId().equals(pId)) {
                playlistEntities.add(toFullDTO(entity));
            }
        }
        return playlistEntities;
    }

    public List<PlaylistDTO> channelPlaylist(Integer channelId) {
        ChanelEntity channelEntity = chanelService.get(channelId);

        List<PlaylistDTO> dtoList = new ArrayList<>();

        List<PlaylistEntity> entityList = playlistRepository.findAllByChanelIdAndStatus(channelEntity.getId(),
                PlaylistStatus.PUBLIC,
                Sort.by(Sort.Direction.DESC, "CreatedDate"));

        entityList.forEach(entity -> {
            dtoList.add(toShortDTO(entity));
        });
        return dtoList;
    }

    public Boolean delete(Integer playlistId, Integer profileId) {
        PlaylistEntity entity = getById(playlistId);

        if (!entity.getChanel().getProfile().getId().equals(profileId)) {
            throw new AppForbiddenException("Not access!");
        }

        playlistRepository.delete(entity);

        return true;
    }

    public PlaylistDTO get(Integer playlistId) {
        PlaylistEntity entity = getById(playlistId);
        return toFullDTO(entity);
    }

    public PlaylistEntity getById(Integer id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Not found!");
                });
    }

    public PlaylistDTO toShortDTO(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        dto.setChanel(chanelService.toDTOShort(entity.getChanel()));

        dto.setVideoCount(playlistVideoRepository.getVideoCountByPlaylistId(entity.getId()));


        List<VideoDTO> videoList = playlistVideoRepository.getTop2VideoByPlaylistId(entity.getId())
                .stream()
                .map(playlistVideoEntity -> {
                    VideoDTO videoDTO = new VideoDTO();
                    videoDTO.setId(playlistVideoEntity.getVideoId());
                    videoDTO.setTitle(playlistVideoEntity.getVideo().getTitle());
                    videoDTO.setKey(playlistVideoEntity.getVideo().getKey());
                    if (playlistVideoEntity.getVideo().getDuration()  != null) {
                        videoDTO.setDuration(Math.toIntExact(playlistVideoEntity.getVideo().getDuration()));
                    }
                    return videoDTO;
                }).toList();

        dto.setVideoList(videoList);
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PlaylistDTO toFullDTO(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        ChanelDTO channelDTO = chanelService.channelShorInfoWithProfile(entity.getChanel());
        dto.setChanel(channelDTO);

        dto.setStatus(entity.getStatus().name());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setOrder_num(entity.getOrder_num());
        return dto;
    }

    public PlaylistDTO getByPlaylist(Integer id) {
        PlaylistEntity entity = getById(id);
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVideoCount(playlistVideoRepository.getVideoCountByPlaylistId(entity.getId()));

        return dto;
    }
}
