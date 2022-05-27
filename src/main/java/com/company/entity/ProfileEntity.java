package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column()
    private String name;
    @Column()
    private String surname;
    @Column()
    private String email;
    @Column()
    private String password;
    @Column()
    private LocalDateTime updatedDate = LocalDateTime.now();
    @Column
    private Boolean visible = true;
    @Column
    private ProfileRole role;
    @Column
    private ProfileStatus status;
    @OneToOne
    @JoinColumn(name = "photo_id")
    private AttachEntity photo;
}
