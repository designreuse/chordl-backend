package com.robotnec.chords.web;

import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.persistence.entity.user.User;
import com.robotnec.chords.service.SecurityService;
import com.robotnec.chords.service.UserService;
import com.robotnec.chords.validator.UserValidator;
import com.robotnec.chords.web.dto.UserDto;
import com.robotnec.chords.web.mapping.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = "application/json;charset=UTF-8")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody UserDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Validation errors", bindingResult);
        }

        userService.save(mapper.map(userDto, User.class));

        securityService.autologin(userDto.getUsername(), userDto.getPasswordConfirm());

        // TODO
        String token = issueToken(userDto.getUsername());

        userDto.setPassword("");

        return ResponseEntity.ok(userDto);
    }

    private String issueToken(String username) {
        return username + "-token1";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity getCurrentUser(Principal principal) {
        return Optional.ofNullable(principal)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User"));
    }
}