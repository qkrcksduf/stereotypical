package io.wisoft.actuating.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ActuatorNotFoundException extends RuntimeException {

  private final String information;

}
