package io.wisoft.actuating.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ActuatingControllerExceptionHandler {

  @ExceptionHandler(ActuatingNotFoundException.class)
  public ResponseEntity<ErrorResponse> ActuatingNotFoundExceptionHandler(ActuatingNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse("ActuatingNotFoundException", e.getActuatingId() + "에 해당하는 데이터가 존재하지 않습니다."));

  }

}
