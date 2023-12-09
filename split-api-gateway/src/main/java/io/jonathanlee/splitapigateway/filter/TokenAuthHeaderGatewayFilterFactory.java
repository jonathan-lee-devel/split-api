package io.jonathanlee.splitapigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class TokenAuthHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

  public static final String REQUEST_USER_USERNAME_HEADER = "X-Requesting-User-Username";

  private static final String PREFERRED_USERNAME = "preferred_username";

  private static final String UNAUTHENTICATED = "unauthenticated";

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) ->
        exchange.getPrincipal()
            .map(principal -> (principal instanceof JwtAuthenticationToken jwtAuthenticationToken) ?
                jwtAuthenticationToken.getTokenAttributes().getOrDefault(PREFERRED_USERNAME, UNAUTHENTICATED).toString() :
                UNAUTHENTICATED)
            .map(username -> withCustomHeader(exchange, username))
            .defaultIfEmpty(exchange)
            .flatMap(chain::filter);
  }

  private ServerWebExchange withCustomHeader(ServerWebExchange exchange, String username) {
    return exchange.mutate()
        .request(request -> request.headers(
            httpHeaders -> {
              httpHeaders.remove(REQUEST_USER_USERNAME_HEADER); // Ensure no impersonated values
              if (!UNAUTHENTICATED.equals(username)) { // Only set header on successful authentication
                httpHeaders.add(REQUEST_USER_USERNAME_HEADER, username);
              }
            }
        )).build();
  }
}
