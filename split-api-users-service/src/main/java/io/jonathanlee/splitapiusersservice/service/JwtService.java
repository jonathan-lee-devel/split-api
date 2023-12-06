package io.jonathanlee.splitapiusersservice.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  boolean isJwtValid(String jwt, UserDetails userDetails);

  String extractUsername(String jwt);

  String generateJwt(UserDetails userDetails);

  String generateJwt(Map<String, Object> extraClaims, UserDetails userDetails);

}
