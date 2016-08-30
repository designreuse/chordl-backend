package com.robotnec.chords.jwt;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtClaims {
    private long userId;
    private String username;
    private Date expiration;
    private Date issuedAt;
}
