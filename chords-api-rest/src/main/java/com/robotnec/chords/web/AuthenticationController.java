package com.robotnec.chords.web;

import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.persistence.entity.user.ChordsUser;
import com.robotnec.chords.service.FacebookService;
import com.robotnec.chords.service.JwtTokenService;
import com.robotnec.chords.service.UserService;
import com.robotnec.chords.web.dto.ChordsUserDto;
import com.robotnec.chords.web.dto.CredentialsDto;
import com.robotnec.chords.web.dto.TokenDto;
import com.robotnec.chords.web.mapping.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.social.facebook.api.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = "application/json;charset=UTF-8")
public class AuthenticationController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity me(Principal principal) {
        return userService.findByEmail(principal.getName())
                .map(chordsUser -> mapper.map(chordsUser, ChordsUserDto.class))
                .map(ResponseEntity::ok)
                .orElse(null);
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity authenticate(@Valid @RequestBody CredentialsDto credentialsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return facebookService.validateFacebookUser(credentialsDto)
                .map(this::findOrCreateChordUser)
                .map(user -> jwtTokenService.generateToken(user))
                .map(TokenDto::new)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AuthorizationServiceException("Can't authenticate"));
    }

    private ChordsUser findOrCreateChordUser(User facebookUser) {
        return userService.findByEmail(facebookUser.getEmail())
                .orElseGet(() -> userService.save(mapper.map(facebookUser, ChordsUser.class)));
    }

}