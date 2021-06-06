package io.wisoft.sensing.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

  private final String title;

  private final String message;

}
