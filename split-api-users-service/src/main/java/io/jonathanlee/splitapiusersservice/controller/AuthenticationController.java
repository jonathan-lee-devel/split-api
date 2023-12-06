package io.jonathanlee.splitapiusersservice.controller;

import io.jonathanlee.splitapiusersservice.service.JwtService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Map<String, Object>> login() {
    return ResponseEntity.ok(
        Map.of("token",
        this.jwtService.generateJwt(this.userDetailsService.loadUserByUsername("jonathan.lee.devel@gmail.com")))
    );
  }

}
