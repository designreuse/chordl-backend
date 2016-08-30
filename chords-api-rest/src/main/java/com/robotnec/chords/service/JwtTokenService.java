package com.robotnec.chords.service;

import com.robotnec.chords.jwt.JwtClaims;
import com.robotnec.chords.persistence.entity.user.ChordsUser;

import java.util.Optional;

public interface JwtTokenService {
    String generateToken(ChordsUser user);

    Optional<JwtClaims> validateToken(String token);
}
