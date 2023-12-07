package io.jonathanlee.splitapiusersservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {

  @NotNull
  private String username;

  @NotNull
  private String password;

}
