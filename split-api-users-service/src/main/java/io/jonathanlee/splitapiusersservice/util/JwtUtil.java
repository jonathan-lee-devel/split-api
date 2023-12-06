package io.jonathanlee.splitapiusersservice.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class JwtUtil {

  private static final String TOKEN_HEADER = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";

  private final String secretKey = "12345";
  private final long accessTokenValidity = 60L * 60L * 1000L;

  private final JwtParser jwtParser;

  public JwtUtil() {
    this.jwtParser = Jwts.parser().build();
  }

}
