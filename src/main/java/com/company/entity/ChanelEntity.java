package com.company.entity;

import com.company.enums.ChanelStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "chanel")
public class ChanelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name = "photo_id")
    private AttachEntity photo;
    @Column
    private String description;
    @Column
    private ChanelStatus status;
    @OneToOne
    @JoinColumn(name = "banner_id")
    private AttachEntity banner;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
    @Column
    private String key;

    @Column
    private LocalDateTime createdDate;
}
