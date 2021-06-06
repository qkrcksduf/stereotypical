package io.wisoft.actuating.ui;

import io.wisoft.actuating.application.ActuatingService;
import io.wisoft.actuating.domain.Actuating;
import io.wisoft.actuating.exception.ActuatingNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stereo-actuatings")
public class ActuatingController {

  private final ActuatingService service;

  @GetMapping
  public ResponseEntity<List<ActuatingResponseDto>> findActuatingList() {
    List<Actuating> actuatingList = service.findActuatingList();
    List<ActuatingResponseDto> result = actuatingList.stream()
        .map(ActuatingResponseDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.status(OK).body(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ActuatingResponseDto> findActuatingById(@PathVariable("id") UUID id) {
    ActuatingResponseDto result;
    Actuating findActuating = service.findActuatingById(id);

    if (findActuating == null) throw new ActuatingNotFoundException(id);
    result = new ActuatingResponseDto(findActuating);


    return ResponseEntity.status(OK).body(result);
  }


  @PostMapping("/{actuator-id}")
  public ResponseEntity<String> actuating(@PathVariable("actuator-id") String actuatorId, @RequestBody ActuatingDto actuatingDto) {
    System.out.println("actuatorId = " + actuatorId);
    System.out.println("actuatingDto = " + actuatingDto.toString());
    service.actuating(actuatorId, actuatingDto);
    return ResponseEntity.status(CREATED).body("제어가 성공적으로 완료되었습니다.");
  }

  @PostMapping("/result")
  public ResponseEntity<String> registerActuatingResult(@RequestBody @Valid ActuatingRegister registerDto) {
    System.out.println(registerDto.toString());
    service.register(registerDto);
    return ResponseEntity.status(CREATED).body("actuating 결과가 성공적으로 등록되었습니다.");
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

  @Getter
  @NoArgsConstructor
  public static class ActuatingResponseDto {

    private UUID actuatorId;
    private String actuatorName;
    private LocalDateTime actuatingTime;
    private String actuatingValue;
    private ActuatingRegister.ActuatingResult result;

    public ActuatingResponseDto(Actuating actuating) {
      this.actuatorId = actuating.getActuatorId();
      this.actuatorName = actuating.getActuatorName();
      this.actuatingTime = actuating.getActuatingTime();
      this.actuatingValue = actuating.getActuatingValue();
      this.result = actuating.getResult();
    }

  }

}
