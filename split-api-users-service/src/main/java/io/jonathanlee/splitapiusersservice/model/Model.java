package io.jonathanlee.splitapiusersservice.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Model {

  private Instant createdAt;

  private Instant updatedAt;

}
