package com.robotnec.chords.web.dto;

import lombok.*;

/**
 * @author zak <zak@robotnec.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChordsUserDto {
    private Long id;
    private String email;
    private String name;
    private String facebookUserId;
    private String facebookLink;
    private boolean hasFavorites;
    private RoleDto role;
}
