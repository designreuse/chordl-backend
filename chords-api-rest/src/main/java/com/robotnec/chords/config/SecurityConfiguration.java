package com.robotnec.chords.config;

import com.robotnec.chords.config.auth.AuthFailureHandler;
import com.robotnec.chords.config.auth.AuthSuccessHandler;
import com.robotnec.chords.config.auth.HttpAuthenticationEntryPoint;
import com.robotnec.chords.config.auth.HttpLogoutSuccessHandler;
import com.robotnec.chords.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableOAuth2Sso
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/auth/**","/swagger**", "/webjars/**", "/configuration/**", "/v2/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .authenticationProvider(authenticationProvider())
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .and();
//                    .and()
//                .logout()
//                    .permitAll()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/auth/login", "DELETE"))
//                    .logoutSuccessHandler(logoutSuccessHandler)
//                    .and()
//                .sessionManagement()
//                .maximumSessions(1);

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}