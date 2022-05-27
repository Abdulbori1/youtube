package com.company.service;

import com.company.dto.PlaylistVideoDTO;
import com.company.entity.PlaylistEntity;
import com.company.entity.PlaylistVideoEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.VideoEntity;
import com.company.enums.VideoStatus;
import com.company.exception.AppForbiddenException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.PlaylistRepository;
import com.company.repository.PlaylistVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class PlaylistVideoService {
    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChanelService chanelService;

    public PlaylistVideoDTO create(PlaylistVideoDTO dto, Integer pId) {
        PlaylistEntity playlist = playlistService.getEntity(dto.getPlaylistId());
        VideoEntity video = videoService.get(dto.getVideoId());
        if (playlist.getUser().getId().equals(pId) && video.getProfile().getId().equals(pId)) {

            PlaylistVideoEntity entity = new PlaylistVideoEntity();
            entity.setCreated_date(LocalDateTime.now());
            entity.setPlaylistId(playlist.getId());
            entity.setVideoId(video.getId());
            entity.setOrder_num(dto.getOrder_num());

            playlistVideoRepository.save(entity);
            dto.setCreated_date(entity.getCreated_date());
            dto.setId(entity.getId());

            return dto;
        }

        throw new ItemNotFoundException("Item not found");
    }

    public PlaylistVideoDTO update(Integer id, PlaylistVideoDTO dto, Integer pId) {
        PlaylistEntity playlist = playlistService.getEntity(dto.getPlaylistId());
        VideoEntity video = videoService.get(dto.getVideoId());
        if (playlist.getUser().getId().equals(pId) && video.getProfile().getId().equals(pId)) {

            PlaylistVideoEntity entity = get(id);
            entity.setCreated_date(LocalDateTime.now());
            entity.setPlaylist(playlist);
            entity.setVideo(video);
            entity.setOrder_num(dto.getOrder_num());

            playlistVideoRepository.save(entity);
            dto.setCreated_date(entity.getCreated_date());
            dto.setId(entity.getId());

            return dto;
        }

        throw new ItemNotFoundException("Item not found");
    }

    public boolean delete(Integer id, Integer pId) {
        PlaylistVideoEntity entity = get(id);
        PlaylistEntity playlist = playlistService.getEntity(entity.getPlaylistId());
        VideoEntity video = videoService.get(entity.getVideoId());
        if (playlist.getUser().getId().equals(pId) && video.getProfile().getId().equals(pId)) {
            playlistVideoRepository.delete(entity);
            return true;
        }

        throw new AppForbiddenException("App forbidden");
    }

    public List<PlaylistVideoDTO> getPlaylistId(Integer playlistId) {
        List<PlaylistVideoDTO> playlistVideoList = new LinkedList<>();
        List<PlaylistVideoEntity> playlistVideoEntities = playlistVideoRepository.findAll();

        if (!playlistVideoEntities.isEmpty()) {
            for (PlaylistVideoEntity entity : playlistVideoEntities) {
                if (entity.getPlaylistId().equals(playlistId)) {
                    playlistVideoList.add(toDto(entity));
                }
            }
        }
        return playlistVideoList;
    }


    public PlaylistVideoEntity get(Integer id) {
        return playlistVideoRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Playlist or Video not found");
        });
    }

    public PlaylistVideoDTO toDto(PlaylistVideoEntity entity) {
        PlaylistVideoDTO dto = new PlaylistVideoDTO();
        dto.setId(entity.getId());
        dto.setOrder_num(entity.getOrder_num());
        dto.setPlaylistId(entity.getPlaylistId());
        dto.setPlaylist(playlistService.get(entity.getPlaylistId()));
        dto.setVideo(videoService.toDTOVideo(videoService.get(entity.getVideoId())));
        dto.setChanel(chanelService.toDTO(chanelService.get(dto.getPlaylist().getChanel().getId())));
        dto.setCreated_date(entity.getCreated_date());

        return dto;
    }
}
