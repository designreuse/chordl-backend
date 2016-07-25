package com.robotnec.chords.persistence.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @DateTimeFormat(style = "M-")
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @DateTimeFormat(style = "M-") // TODO @CreatedDate
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
