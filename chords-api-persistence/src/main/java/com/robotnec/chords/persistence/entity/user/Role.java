package com.robotnec.chords.persistence.entity.user;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<ChordsUser> users;
}