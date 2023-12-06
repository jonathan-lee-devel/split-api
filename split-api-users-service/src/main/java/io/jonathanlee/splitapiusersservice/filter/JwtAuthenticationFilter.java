package io.jonathanlee.splitapiusersservice.filter;

import io.jonathanlee.splitapiusersservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_TOKEN_PREFIX = "Bearer ";

  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
    if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_TOKEN_PREFIX)) {
      final HttpStatus responseHttpStatus = HttpStatus.UNAUTHORIZED;
      log.info("Missing auth header, setting status to: {}", responseHttpStatus);
      response.setStatus(responseHttpStatus.value());
      return; // Stop filter chain, return HTTP Unauthorized response
    }
    final String accessToken = authorizationHeader.substring(BEARER_TOKEN_PREFIX.length());
    final String username = this.jwtService.extractUsername(accessToken);
    if (
        username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null // User is not already authenticated
    ) {
      final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (this.jwtService.isJwtValid(accessToken, userDetails)) {
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } else {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return; // Stop filter chain, return HTTP Unauthorized response
      }
    }
    filterChain.doFilter(request, response);
  }
}
