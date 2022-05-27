package com.company.repository;

import com.company.entity.VideoEntity;
import com.company.entity.VideoTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoTagRepository extends JpaRepository<VideoTagEntity, Integer> {
    @Query(value = "select * from video_tag where video_id = :playlistId "
            , nativeQuery = true)
    List<VideoTagEntity> getTop2VideoByPlaylistId(@Param("playlistId") Integer playlistId);
}
