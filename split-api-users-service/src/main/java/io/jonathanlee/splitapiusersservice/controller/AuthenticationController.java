package io.jonathanlee.splitapiusersservice.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  public static final String REQUEST_USER_USERNAME_HEADER = "X-Requesting-User-Username";


  @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Map<String, Object>> login(
      @RequestHeader(REQUEST_USER_USERNAME_HEADER) String requestingUserUsername
  ) {
    return ResponseEntity.ok(Map.of(
        "response", "through-gateway",
        "username-header", requestingUserUsername,
        "username-context", SecurityContextHolder.getContext().getAuthentication().getPrincipal()
    ));
  }

}
