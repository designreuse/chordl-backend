package com.robotnec.chords.service.impl;

import com.robotnec.chords.facebook.FacebookCheckTokenResponseDto;
import com.robotnec.chords.service.FacebookService;
import com.robotnec.chords.web.dto.CredentialsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
public class FacebookServiceImpl implements FacebookService {

    @Value("${spring.social.facebook.app-id}")
    private String fbAppId;

    @Value("${spring.social.facebook.app-secret}")
    private String fbAppSecret;

    @Override
    public Optional<User> validateFacebookUser(CredentialsDto credentials) {
        log.debug("Check user token");

        String applicationAccessToken = fetchApplicationAccessToken();

        log.debug("Got app access token: {}", applicationAccessToken);

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/debug_token")
                        .queryParam("input_token", credentials.getSocialToken())
                        .queryParam("access_token", applicationAccessToken);

        RestTemplate restTemplate = new RestTemplate();
        URI url = builder.build().encode().toUri();

        log.debug("Send request to FB: {}", url);

        ResponseEntity<FacebookCheckTokenResponseDto> response =
                restTemplate.getForEntity(
                        url,
                        FacebookCheckTokenResponseDto.class);

        FacebookCheckTokenResponseDto responseBody = response.getBody();
        FacebookCheckTokenResponseDto.Data data = responseBody.getData();

        User userProfile = null;

        log.debug("Got data {}", data);

        if (data.getError() == null) {
            if (data.isValid()
                    && data.getAppId().equals(fbAppId)
                    && data.getUserId().equals(credentials.getUserId())) {
                Facebook facebook = new FacebookTemplate(credentials.getSocialToken());
                userProfile = facebook.userOperations().getUserProfile();
                log.debug("Token valid!");
            } else {
                log.warn("Token is not valid!");
            }
        } else {
            log.error("Checking token produces error, {}", data.getError());
        }

        return Optional.ofNullable(userProfile);
    }


    private String fetchApplicationAccessToken() {
        return fbAppId + "|" + fbAppSecret;
    }
}
