package io.wisoft.virtual.cnc.ui;

import io.wisoft.virtual.cnc.application.CncService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/virtual-cncs")
@RequiredArgsConstructor
//@EnableAsync
public class CncController {

  private final CncService service;

  @PostMapping("async-actuating")
  @Async
  public ListenableFuture<ResponseEntity<String>> asyncActuating(@RequestBody ActuatingDto actuatingDto) {
    System.out.println("actuatingDto.toString() = " + actuatingDto.toString());
    service.asyncActuating(actuatingDto);
    return AsyncResult.forValue(ResponseEntity.ok().body("async-actuating"));
  }

  @PostMapping("sync-actuating")
  public ResponseEntity<String> syncActuating(@RequestBody ActuatingDto actuatingDto) {
    System.out.println("actuatingDto.toString() = " + actuatingDto.toString());
    service.syncActuating(actuatingDto);
    return ResponseEntity.ok().body("sync-actuating");
  }

  @Getter
  @ToString
  public static class ActuatingDto {
    private UUID actuatingId;
    private UUID actuatorId;
    private String actuatorName;
    private String actuatingValue;
    private String protocol;
  }
}
