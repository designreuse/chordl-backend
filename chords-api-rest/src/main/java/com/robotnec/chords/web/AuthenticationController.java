package com.robotnec.chords.web;

import com.robotnec.chords.service.JwtTokenService;
import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.persistence.entity.user.ChordsUser;
import com.robotnec.chords.service.UserService;
import com.robotnec.chords.validator.UserValidator;
import com.robotnec.chords.web.dto.TokenDto;
import com.robotnec.chords.web.dto.UserDto;
import com.robotnec.chords.web.mapping.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal getAuthenticatedUser(Principal principal) {
//        String token = request.getHeader(tokenHeader);
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        log.debug("get user, token: " + token + " username: " + username);
        return principal;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto, BindingResult bindingResult) throws AuthenticationException {
        if (userService.findByUsername(userDto.getUsername()) == null) {
            userValidator.validate(userDto, bindingResult);
            if (bindingResult.hasErrors()) {
                throw new InvalidRequestException("Validation errors", bindingResult);
            }

            userService.save(mapper.map(userDto, ChordsUser.class));
        }

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final ChordsUser userDetails = userService.findByUsername(userDto.getUsername());
        final String token = jwtTokenService.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new TokenDto(token));
    }

//    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
//    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
//        String token = request.getHeader(tokenHeader);
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        User user = (User) userDetailsService.loadUserByUsername(username);
//
//        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
//            String refreshedToken = jwtTokenUtil.refreshToken(token);
//            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//        } else {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

}