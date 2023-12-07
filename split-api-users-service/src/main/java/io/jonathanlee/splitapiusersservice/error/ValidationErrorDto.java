package io.jonathanlee.splitapiusersservice.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDto {

  private String field;

  private String message;

}
