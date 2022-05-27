package com.company.entity;

import com.company.enums.AttachType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;
    @Column
    private String origenName;
    @Column
    private Long size;
    @Column
    private AttachType type;
    @Column
    private String path;
    @Column
    private String Extension;
    @Column
    private LocalDateTime createdDate;
}
