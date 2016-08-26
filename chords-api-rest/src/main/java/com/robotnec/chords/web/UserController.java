package com.robotnec.chords.web;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class UserController {

    @RequestMapping(produces = "text/plain;charset=UTF-8")
    public String home(Principal principal) {
        if (principal != null) {
            LinkedHashMap details = (LinkedHashMap) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            return (String) details.get("name");
        }
        return "No user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal user(Principal principal) {
        return principal;
    }
}
