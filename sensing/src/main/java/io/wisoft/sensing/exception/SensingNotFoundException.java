package io.wisoft.sensing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SensingNotFoundException extends RuntimeException {

  private UUID sensingId;

}
