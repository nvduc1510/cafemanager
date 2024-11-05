package com.example.BackEnd.controller;

import com.example.BackEnd.config.jwt.AuthUserDetails;
import com.example.BackEnd.config.jwt.JWTokenProvider;
import com.example.BackEnd.config.jwt.UserDetailsServiceImpl;
import com.example.BackEnd.dto.request.LoginRequest;
import com.example.BackEnd.dto.request.RegisterRequest;
import com.example.BackEnd.dto.response.LoginResponse;
import com.example.BackEnd.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    final JWTokenProvider tokenProvider;
    final AuthenticationManager authenticationManager;
    final UserDetailsServiceImpl userDetailsService;

    AuthController(AuthenticationManager authenticationManager, JWTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProvider.generateToken((AuthUserDetails) authentication.getPrincipal());
            return new LoginResponse(accessToken);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            log.warn(ex.getMessage());
            errors.put("code", "100");
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            errors.put("code", "000");
        }
        return new LoginResponse(errors);
    }
    @PostMapping("/register")
    private ResponseEntity<?> createUser (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
    @PutMapping("/reset_password")
    private ResponseEntity<?> forgetPassword (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.forgetPassword(request));
    }
}
