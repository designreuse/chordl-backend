package com.robotnec.chords.persistence.entity.user;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "user")
public class ChordsUser {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String facebookUserId;
    private String facebookLink;
    private boolean hasFavorites;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}