package io.wisoft.sensing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SensingControllerExceptionHandler {

  @ExceptionHandler(SensingNotFoundException.class)
  public ResponseEntity<ErrorResponse> sensingNotFoundExceptionHandler(SensingNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ErrorResponse("SensingNotFoundException",
                e.getSensingId() + "에 해당하는 데이터가 존재하지 않습니다."));
  }

}
