package io.wisoft.actuating.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CncNotFoundException extends RuntimeException {

  @Getter
  final String information;

}