package io.jonathanlee.splitapiusersservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  @Value("${split.jwt.signing-key}")
  private String jwtSigningKey;

  @Value("#{new Long('${split.jwt.expiration-time-hours}')}")
  private Long jwtExpirationTimeHours;

  @Override
  public boolean isJwtValid(String jwt, UserDetails userDetails) {
    final String username = this.extractUsername(jwt);
    return
        username != null &&
        (username.equals(userDetails.getUsername())) &&
        isJwtNotExpired(jwt);
  }

  @Override
  public String extractUsername(String jwt) {
    return this.extractClaim(jwt, Claims::getSubject);
  }

  @Override
  public String generateJwt(UserDetails userDetails) {
    return this.generateJwt(Collections.emptyMap(), userDetails);
  }

  @Override
  public String generateJwt(Map<String, Object> extraClaims, UserDetails userDetails) {;
    return Jwts
        .builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plus(this.jwtExpirationTimeHours, ChronoUnit.HOURS)))
        .signWith(getSigningKey())
        .compact();
  }

  private boolean isJwtNotExpired(String jwt) {
    return this.extractExpiration(jwt).after(new Date());
  }

  public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(jwt));
  }

  private Date extractExpiration(String jwt) {
    return this.extractClaim(jwt, Claims::getExpiration);
  }

  private Claims extractAllClaims(String jwt) {
    return Jwts
        .parser()
        .verifyWith(this.getSigningKey())
        .build()
        .parseSignedClaims(jwt)
        .getPayload();
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSigningKey));
  }

}
