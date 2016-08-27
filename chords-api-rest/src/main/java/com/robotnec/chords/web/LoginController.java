package com.robotnec.chords.web;

import com.robotnec.chords.web.dto.FacebookTokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
public class LoginController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(FacebookTokenDto facebookToken) {
        return ResponseEntity.ok(facebookToken.getAccessToken());
    }
}
