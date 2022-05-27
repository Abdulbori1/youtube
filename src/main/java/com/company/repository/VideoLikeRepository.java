package com.company.repository;

import com.company.entity.VideoLikeEntity;
import com.company.enums.VideoLikeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, Integer> {
    Integer countByVideoIdAndType(Integer videoId, VideoLikeType like);

    VideoLikeEntity findByVideoIdAndProfileId(Integer id, Integer profileId);
}
