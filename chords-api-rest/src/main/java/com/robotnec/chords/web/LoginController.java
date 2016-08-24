package com.robotnec.chords.web;

import org.springframework.web.bind.annotation.RequestMapping;
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
}
