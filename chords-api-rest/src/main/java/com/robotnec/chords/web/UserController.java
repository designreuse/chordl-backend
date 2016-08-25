package com.robotnec.chords.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/signin", produces = "application/json;charset=UTF-8")
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal user(Principal principal) {
        return principal;
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public Principal signInThroughFacebook(Principal principal) {

        return principal;
    }
}
