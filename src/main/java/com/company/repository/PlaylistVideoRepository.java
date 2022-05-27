package com.company.repository;

import com.company.entity.PlaylistVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideoEntity, Integer> {
    List<PlaylistVideoEntity> findByPlaylistId(Integer id);

    @Query("select count(id) from PlaylistVideoEntity where playlistId = :playlistId")
    int getVideoCountByPlaylistId(@Param("playlistId") Integer playlistId);

    @Query(value = "select * from playlist_video where playlist_id = :playlistId order by order_num limit 2"
            , nativeQuery = true)
    List<PlaylistVideoEntity> getTop2VideoByPlaylistId(@Param("playlistId") Integer playlistId);
}
