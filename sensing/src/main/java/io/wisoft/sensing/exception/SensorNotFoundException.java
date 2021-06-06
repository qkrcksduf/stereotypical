package io.wisoft.sensing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorNotFoundException extends RuntimeException {

  private final String information;

}
