package com.company.repository;

import com.company.entity.PlaylistVideoEntity;
import com.company.entity.TagEntity;
import com.company.entity.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<VideoEntity, Integer> {

    Page<VideoEntity> findByCategoryId(Pageable pageable, Integer categoryId);

    Optional<VideoEntity> findByKey(String key);

    List<VideoEntity> findByTitle(String key);

    @Query(value = "select v.id, v.key, v.title, v.preview_attach_id, " +
            "       v.published_date from video as v where v.chanel_id=:ch", nativeQuery = true)
    List<VideoEntity> VidePlayListInfo(@Param("ch") Integer id);

    @Query(value = "select * " +
            "from video as v where category_id=:categoryId", nativeQuery = true)
    Page<VideoEntity> VideShortInfoCategory(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Query(value = "select *" +
            "from video as v where title=:title", nativeQuery = true)
    List<VideoEntity> VideShortInfoTitle(@Param("title") String title);

    @Query(value = "select v.id, v.key, v.title, v.preview_attach_id," +
            "        v.description, v.attach_id, v.published_date, v.chanel_id, v.view_count, " +
            "        v.category_id, v.tag_list, v.created_date, v.duration, v.is_user_disliked, v.is_user_liked," +
            "        v.profile_id, v.like_count, v.dislike_count,  v.status, v.type " +
            "        from video as v inner join video_tag t on v.id = t.video_id" +
            "        where t.id=:id order by t.created_date desc", nativeQuery = true)
    Page<VideoEntity> VideShortInfoTag(@Param("id") Integer id, Pageable pageable);

    @Query(value = "select v.id, v.key, v.title, v.description," +
            "       v.preview_attach_id, v.attach_id," +
            "       v.category_id, v.tag_list, v.created_date, v.duration, v.is_user_disliked, v.is_user_liked, " +
            "       v.profile_id, v.status, v.type, " +
            "       v.published_date, v.chanel_id," +
            "       v.view_count, v.like_count, v.dislike_count from video as v where v.key=:key", nativeQuery = true)
    Optional<VideoEntity> VideFullInfoKey(@Param("key") String key);

    @Query(value = "select v.id, v.key, v.title, v.preview_attach_id," +
            "        v.description, v.attach_id, v.published_date, v.chanel_id, v.view_count, " +
            "        v.category_id, v.tag_list, v.created_date, v.duration, v.is_user_disliked, v.is_user_liked," +
            "        v.profile_id, v.like_count, v.dislike_count,  v.status, v.type " +
            "        from video as v inner join video_tag t on v.id = t.video_id" +
            "        where v.chanel_id=:id order by v.created_date desc", nativeQuery = true)
    Page<VideoEntity> VideShortInfoChanel(@Param("id") Integer id, Pageable pageable);
}
