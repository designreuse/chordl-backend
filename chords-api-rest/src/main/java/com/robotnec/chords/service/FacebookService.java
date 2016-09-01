package com.robotnec.chords.service;

import org.springframework.social.facebook.api.User;

import java.util.Optional;

public interface FacebookService {
    Optional<User> checkUserToken(String userToken);
}
