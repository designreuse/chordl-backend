package com.robotnec.chords.config.jwt;

import com.robotnec.chords.jwt.JwtClaims;
import com.robotnec.chords.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.scheme}")
    private String tokenScheme;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader(tokenHeader);

        if (authHeader == null || authHeader.isEmpty()) {
            log.debug("Anonymous authentication");
            chain.doFilter(request, response);
            return;
        }

        try {
            boolean authenticated = isAlreadyAuthenticated() || validateAuthHeaderAndGetToken(authHeader)
                    .flatMap((token) -> jwtTokenService.validateToken(token))
                    .map(jwtClaims -> authenticate(jwtClaims, httpRequest))
                    .orElse(false);

            if (authenticated) {
                log.debug("Successfully authenticated");
            } else {
                log.warn("Wasn't authenticated");
            }
        } catch (Exception e) {
            log.warn("Authentication filter exception", e);
        } finally {
            chain.doFilter(request, response);
        }
    }

    private boolean authenticate(JwtClaims jwtClaims, HttpServletRequest httpRequest) {
        log.debug("Got token claims: " + jwtClaims);

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtClaims.getEmail());

        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } else {
            log.warn(String.format("UserDetails for user '%s' not found", jwtClaims.getUsername()));
            return false;
        }
    }

    private Optional<String> validateAuthHeaderAndGetToken(String authHeader) {
        String jwtToken = null;

        if (authHeader != null) {
            String[] schemeAndToken = authHeader.split("\\s+");

            if (schemeAndToken.length == 2) {
                String scheme = schemeAndToken[0];

                if (scheme.equals(tokenScheme)) {
                    jwtToken = schemeAndToken[1];
                } else {
                    log.error(String.format("Scheme '%s' not supported", scheme));
                }
            } else {
                log.error("Must supply token in '<scheme> <token>' format");
            }

        } else {
            log.warn("Missing Authorization header in request");
        }

        return Optional.ofNullable(jwtToken);
    }

    private boolean isAlreadyAuthenticated() {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        return existingAuth != null && existingAuth.isAuthenticated();
    }
}