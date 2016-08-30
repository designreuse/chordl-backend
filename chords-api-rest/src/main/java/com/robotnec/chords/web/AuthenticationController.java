package com.robotnec.chords.web;

import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.persistence.entity.user.ChordsUser;
import com.robotnec.chords.service.JwtTokenService;
import com.robotnec.chords.service.UserService;
import com.robotnec.chords.web.dto.CredentialsDto;
import com.robotnec.chords.web.dto.TokenDto;
import com.robotnec.chords.web.mapping.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal getAuthenticatedUser(Principal principal) {
        return principal;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@Valid @RequestBody CredentialsDto credentialsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        if (userService.findByUsername(credentialsDto.getUserId()) == null) {
            userService.save(mapper.map(credentialsDto, ChordsUser.class));
        }

        ChordsUser user = userService.findByUsername(credentialsDto.getUserId());

        return ResponseEntity.ok(new TokenDto(jwtTokenService.generateToken(user)));
    }
}