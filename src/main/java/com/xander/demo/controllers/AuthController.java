package com.xander.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.oauth2.sdk.AccessTokenResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  @PreAuthorize("permitAll()")
  public ResponseEntity<LoginResponseMessage> login(String email, String pass) {
    var responseMessage = authService.login(loginRequestMessage);

    return ResponseEntity.status(HttpStatus.OK)
        .body(responseMessage);
  }
}
