package io.jonathanlee.splitapigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;

@Configuration
public class SecurityConfig {

  @Value("${split.jwt.jwk-set-uri}")
  private String jwkSetUri;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
        .pathMatchers("/users/auth/**")
        .permitAll()
        .anyExchange()
        .authenticated())
        .oauth2ResourceServer(oAuth2ResourceServerSpec ->
            oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwkSetUri(this.jwkSetUri))
        );
    http.headers(headerSpec -> headerSpec.frameOptions(frameOptionsSpec -> frameOptionsSpec.mode(Mode.SAMEORIGIN)));
    http.csrf(CsrfSpec::disable);
    return http.build();
  }

  @Bean
  public ReactiveJwtDecoder reactiveJwtDecoder() {
    return new NimbusReactiveJwtDecoder(this.jwkSetUri);
  }

}
