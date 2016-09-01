package com.robotnec.chords.service;

import com.robotnec.chords.web.dto.CredentialsDto;
import org.springframework.social.facebook.api.User;

import java.util.Optional;

public interface FacebookService {
    Optional<User> validateFacebookUser(CredentialsDto credentials);
}
