package com.company.entity;

import com.company.enums.VideoStatus;
import com.company.enums.VideoType;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime publishedDate;
    @Column
    private VideoStatus status;
    @Column
    private String key;
    @Column
    private VideoType type;
    @Column
    private Integer view_count;
    @Column
    private Long duration;
    @Column
    private Integer likeCount;
    @Column
    private Integer dislikeCount;
    @Column
    private Boolean isUserLiked;
    @Column
    private Boolean isUserDisliked;
    @OneToMany
    @JoinColumn(name = "tag_list")
    private List<TagEntity> tagList;
    //    shared_count,(like_count,dislike_count)

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "preview_attach_id", nullable = false)
    private String previewAttachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity previewAttach;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "attach_id", nullable = false)
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "chanel_id", nullable = false)
    private Integer chanelId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chanel_id", insertable = false, updatable = false)
    private ChanelEntity chanel;
}
