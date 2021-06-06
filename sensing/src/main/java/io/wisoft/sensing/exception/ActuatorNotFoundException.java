package io.wisoft.sensing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ActuatorNotFoundException extends RuntimeException {

  private final String actuatorId;

}
