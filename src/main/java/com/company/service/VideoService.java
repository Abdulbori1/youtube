package com.company.service;

import com.company.dto.TagDTO;
import com.company.dto.VideoDTO;
import com.company.entity.*;
import com.company.enums.ProfileRole;
import com.company.enums.VideoLikeType;
import com.company.enums.VideoStatus;
import com.company.enums.VideoType;
import com.company.exception.ItemNotFoundException;
import com.company.repository.VideoLikeRepository;
import com.company.repository.VideoRepository;
import com.company.repository.VideoTagRepository;
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
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ChanelService chanelService;
    @Autowired
    private VideoLikeRepository videoLikeRepository;
    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private TagService tagService;


    public VideoDTO create(VideoDTO dto, Integer pId) {
        VideoEntity entity = new VideoEntity();
        if (dto.getPreviewAttachId() == null) {
            entity.setPreviewAttachId("761e81c1-02d1-418c-aa2a-1fec9ee1885f");
        } else {
            AttachEntity preview = attachService.get(dto.getPreviewAttachId());
            entity.setAttachId(preview.getId());
        }
        if (dto.getPreviewAttachId() == null) {
            entity.setAttachId("761e81c1-02d1-418c-aa2a-1fec9ee1885f");
        } else {
            AttachEntity attach = attachService.get(dto.getAttachId());
            entity.setAttachId(attach.getId());
        }

        CategoryEntity category = categoryService.get(dto.getCategoryId());
        ChanelEntity chanel = chanelService.get(dto.getChanelId());
        ProfileEntity profile = profileService.get(pId);

        entity.setTitle(dto.getTitle());
        entity.setCategoryId(category.getId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPublishedDate(LocalDateTime.now());
        entity.setStatus(VideoStatus.valueOf(dto.getStatus()));
        entity.setDescription(dto.getDescription());
        entity.setChanelId(chanel.getId());
        entity.setDuration(Long.valueOf(dto.getDuration()));

        String key = UUID.randomUUID().toString();
        entity.setKey(key);
        entity.setType(VideoType.valueOf(dto.getType()));
        entity.setProfileId(profile.getId());

        videoRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreatedDate());
        dto.setPublished_date(entity.getPublishedDate());
        dto.setKey(entity.getKey());
        dto.setProfileId(entity.getProfileId());
        dto.setPreviewAttachId(entity.getPreviewAttachId());
        dto.setAttachId(entity.getAttachId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setChanelId(entity.getChanelId());
        dto.setProfileId(entity.getProfileId());

        return dto;
    }

    public VideoDTO updateVideoDetail(VideoDTO dto, Integer id, Integer pId) {
        ProfileEntity profile = profileService.get(pId);

        VideoEntity entity = get(id);
        if (profile.getRole().equals(ProfileRole.ADMIN) || entity.getProfile().equals(profile)) {
            entity.setProfileId(profile.getId());
            if (dto.getPreviewAttachId() != null) {
                AttachEntity preview = attachService.get(dto.getPreviewAttachId());
                entity.setPreviewAttach(preview);
            }
            if (dto.getCategory() != null) {
                CategoryEntity category = categoryService.get(dto.getCategoryId());
                entity.setCategory(category);
            }
            if (dto.getAttach() != null) {
                AttachEntity attach = attachService.get(dto.getAttachId());
                entity.setAttach(attach);
            }

            entity.setTitle(dto.getTitle());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setPublishedDate(LocalDateTime.now());
            entity.setStatus(VideoStatus.valueOf(dto.getStatus()));
            entity.setType(VideoType.valueOf(dto.getType()));

            videoRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreated_date(entity.getCreatedDate());
            dto.setPublished_date(entity.getPublishedDate());
            dto.setProfileId(entity.getProfileId());
            dto.setKey(entity.getKey());
            return dto;
        }
        throw new ItemNotFoundException("Video not valid");
    }

    public VideoDTO updateVideoStatus(VideoDTO dto, Integer id, Integer pId) {
        ProfileEntity profile = profileService.get(pId);

        VideoEntity entity = get(id);
        if (profile.getRole().equals(ProfileRole.ADMIN) || entity.getProfile().equals(profile)) {
            entity.setStatus(VideoStatus.valueOf(dto.getStatus()));

            videoRepository.save(entity);
            dto.setId(entity.getId());

            return dto;
        }
        throw new ItemNotFoundException("Video not valid");
    }

    public List<VideoDTO> GetVideoPaginationByCategoryId(int page, int size, Integer categoryId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "created_date"));

        List<VideoDTO> dtoList = new ArrayList<>();
        videoRepository.VideShortInfoCategory(categoryId, pageable).stream().forEach(entity -> {
            dtoList.add(toDTOShort(entity));
        });

        return dtoList;
    }

    public List<VideoDTO> GetVideoPaginationByTagId(int page, int size, Integer tagId) {
        Pageable pageable = PageRequest.of(page, size);

        List<VideoDTO> dtoList = new ArrayList<>();
        videoRepository.VideShortInfoTag(tagId, pageable).stream().forEach(entity -> {
            dtoList.add(toDTOShort(entity));
        });

        return dtoList;
    }

    public List<VideoDTO> GetVideoPaginationBy(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<VideoDTO> dtoList = new ArrayList<>();
        videoRepository.findAll(pageable).stream().forEach(entity -> {
            dtoList.add(toDTOShort(entity));
        });

        return dtoList;
    }

    public List<VideoDTO> GetVideoPaginationByChanelId(int page, int size, Integer chanelId) {
        Pageable pageable = PageRequest.of(page, size);

        List<VideoDTO> dtoList = new ArrayList<>();
        videoRepository.VideShortInfoChanel(chanelId, pageable).stream().forEach(entity -> {
            dtoList.add(toDTOPlaylist(entity));
        });

        return dtoList;
    }

    public VideoDTO getVideoByKey(Integer pId, String key) {
        ProfileEntity profile = profileService.get(pId);
        VideoEntity entity = getKey(key);
        if (profile.getRole().equals(ProfileRole.ADMIN) || entity.getProfile().equals(profile)) {
            return toDTOVideo(entity);
        } else if (entity.getStatus().equals(VideoStatus.PUBLIC)) {
            return toDTOVideo(entity);
        }
        throw new ItemNotFoundException("Item not found");
    }

    public List<VideoDTO> getVideoByTitle(Integer pId, String title) {
        ProfileEntity profile = profileService.get(pId);
        List<VideoEntity> entityList = getTitle(title);
        List<VideoDTO> entityDTOList = new LinkedList<>();
        for (VideoEntity entity : entityList) {
            if (profile.getRole().equals(ProfileRole.ADMIN) || entity.getProfile().equals(profile)) {
                entityDTOList.add(toDTOShort(entity));
            } else if (entity.getStatus().equals(VideoStatus.PUBLIC)) {
                entityDTOList.add(toDTOShort(entity));
            }
        }
        return entityDTOList;
    }

    public VideoEntity get(Integer id) {
        return videoRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }

    public VideoEntity getKey(String keys) {
        return videoRepository.VideFullInfoKey(keys).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }

    public List<VideoEntity> getTitle(String title) {
        return videoRepository.VideShortInfoTitle(title);
    }

    public VideoDTO toDTOShort(VideoEntity entity) {
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setTitle(entity.getTitle());
        dto.setPublished_date(entity.getPublishedDate());
        dto.setPreviewAttach(attachService.toDTO(entity.getPreviewAttach()));
        dto.setChanel(chanelService.toDTO(entity.getChanel()));
        dto.setView_count(entity.getView_count());
        if (dto.getDuration() != null) {
            dto.setDuration(Math.toIntExact(entity.getDuration()));
        }
        return dto;
    }

    public VideoDTO toDTOVideo(VideoEntity entity) {
        List<TagEntity> tagList = new LinkedList<>();

        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPreviewAttach(attachService.toDTO(entity.getPreviewAttach()));
        dto.setAttach(attachService.toDTO(entity.getAttach()));
        dto.setCategory(categoryService.toDTO(entity.getCategory()));
        dto.setChanel(chanelService.toDTO(entity.getChanel()));
        dto.setPublished_date(entity.getPublishedDate());
        dto.setView_count(entity.getView_count());
        dto.setLikeCount(videoLikeRepository.countByVideoIdAndType(entity.getId(), VideoLikeType.LIKE));
        dto.setDislikeCount(videoLikeRepository.countByVideoIdAndType(entity.getId(), VideoLikeType.DISLIKE));
        List<TagDTO> videoList = videoTagRepository.getTop2VideoByPlaylistId(entity.getId())
                .stream()
                .map(playlistVideoEntity -> {
                    TagDTO tag = new TagDTO();
                    tag.setName(playlistVideoEntity.getTag().getName());
                    tag.setCreatedDate(playlistVideoEntity.getCreatedDate());
                    tag.setId(playlistVideoEntity.getId());
                    return tag;
                }).toList();
        dto.setTagList(videoList);
        if (entity.getDuration() != null) {
            dto.setDuration(Math.toIntExact(entity.getDuration()));
        }

        entity.setLikeCount(dto.getLikeCount());
        entity.setDislikeCount(dto.getDislikeCount());
        entity.setIsUserLiked(dto.getIsUserLiked());
        entity.setIsUserDisliked(dto.getIsUserDisliked());
        videoRepository.save(entity);
        return dto;
    }

    public VideoDTO toDTOPlaylist(VideoEntity entity) {
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setTitle(entity.getTitle());
        dto.setPublished_date(entity.getPublishedDate());
        dto.setPreviewAttach(attachService.toDTOPlaylist(entity.getPreviewAttach()));
        if (dto.getDuration() != null) {
            dto.setDuration(Math.toIntExact(entity.getDuration()));
        }
        return dto;
    }
}
