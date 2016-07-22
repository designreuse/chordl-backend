package com.robotnec.chords.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity {

    @DateTimeFormat(style = "M-")
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @DateTimeFormat(style = "M-")
    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

    @PrePersist
    public void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = this.createdDate;
    }

    @PreUpdate
    public void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
