package com.robotnec.chords.service.impl;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.robotnec.chords.jwt.JwtClaims;
import com.robotnec.chords.persistence.entity.user.ChordsUser;
import com.robotnec.chords.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String CLAIM_KEY_SUB = "SUB";
    private static final String CLAIM_KEY_EXP = "exp";
    private static final String CLAIM_KEY_IAT = "iat";
    private static final String CLAIM_KEY_NAME = "name";

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(ChordsUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_EXP, createExpiration());
        claims.put(CLAIM_KEY_IAT, createIssuedAt());
        claims.put(CLAIM_KEY_SUB, user.getId());
        claims.put(CLAIM_KEY_NAME, user.getUsername());
        return new JWTSigner(secret).sign(claims);
    }

    @Override
    public Optional<JwtClaims> validateToken(String token) {
        log.debug("Validate token: ", token);

        JwtClaims jwtClaims = null;

        Map<String, Object> claims;

        try {
            JWTVerifier verifier = new JWTVerifier(secret);
            claims = verifier.verify(token);
        } catch (Exception e) {
            log.warn("Can't verify token", e.getMessage());
            return Optional.empty();
        }

        Long userId = (Long) claims.get(CLAIM_KEY_SUB);
        String username = (String) claims.get(CLAIM_KEY_NAME);
        Long expired = (Long) claims.get(CLAIM_KEY_EXP);
        Long issuedAt = (Long) claims.get(CLAIM_KEY_IAT);

        Date expiredDate = new Date(expired * 1000);
        Date issuedAtDate = new Date(issuedAt * 1000);

        if (!isTokenExpired(expiredDate)) {
            jwtClaims = JwtClaims.builder()
                    .userId(userId)
                    .username(username)
                    .expiration(expiredDate)
                    .issuedAt(issuedAtDate)
                    .build();
        } else {
            log.warn("Token was expired at: " + expiredDate);
        }

        return Optional.ofNullable(jwtClaims);
    }

    private Boolean isTokenExpired(Date expired) {
        return expired.before(new Date());
    }

    private long createIssuedAt() {
        return System.currentTimeMillis() / 1000L;
    }

    private long createExpiration() {
        return (System.currentTimeMillis() + expiration * 1000) / 1000L;
    }

}