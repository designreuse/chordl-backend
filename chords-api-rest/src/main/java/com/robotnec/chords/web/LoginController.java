package com.robotnec.chords.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController(value = "/login")
public class LoginController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @RequestMapping("/success")
    public ResponseEntity success() {
        return ResponseEntity.ok("{status: ok}");
    }
}
