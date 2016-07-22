package com.robotnec.chords.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity {

    @DateTimeFormat(style = "M-")
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @DateTimeFormat(style = "M-")
    @Column(name = "updated_date")
    private Date updatedDate = new Date();

    @PrePersist
    public void onCreate() {
        createdDate = new Date();
        updatedDate = this.createdDate;
    }

    @PreUpdate
    public void onUpdate() {
        updatedDate = new Date();
    }
}
