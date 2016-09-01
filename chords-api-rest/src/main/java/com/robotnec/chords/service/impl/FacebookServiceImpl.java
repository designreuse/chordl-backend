package com.robotnec.chords.service.impl;

import com.robotnec.chords.service.FacebookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Slf4j
@Service
public class FacebookServiceImpl implements FacebookService {

    @Value("${spring.social.facebook.app-id}")
    private String fbAppId;

    @Value("${spring.social.facebook.app-secret}")
    private String fbAppSecret;

    @Override
    public Optional<User> checkUserToken(String userToken) {
        log.debug("Check user token");

        Facebook facebook = new FacebookTemplate(userToken);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/debug_token")
                .queryParam("input_token", userToken)
                .queryParam("access_token", fetchApplicationAccessToken());

        ResponseEntity<String> response = facebook.restOperations().getForEntity(
                builder.build().encode().toUri(),
                String.class);

        log.debug("Check result: " + response.getBody());

        return Optional.ofNullable(facebook.userOperations().getUserProfile());
    }


    private String fetchApplicationAccessToken() {
        OAuth2Operations oauth = new FacebookConnectionFactory(fbAppId, fbAppSecret).getOAuthOperations();
        return oauth.authenticateClient().getAccessToken();
    }
}
