package io.wisoft.sensing.ui;

import io.wisoft.sensing.application.SensingService;
import io.wisoft.sensing.domain.Sensing;
import io.wisoft.sensing.exception.SensingNotFoundException;
import io.wisoft.sensing.ui.SensingDto.SensingRegister;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stereo-sensings")
public class SensingController {

  private final SensingService service;

  @GetMapping
  public ResponseEntity<List<SensingResponseDto>> findSensingList() {
    List<Sensing> sensingList = service.findSensingList();
    List<SensingResponseDto> result = sensingList.stream()
        .map(SensingResponseDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.status(OK).body(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SensingResponseDto> findSensingById(@PathVariable("id") UUID id) {
    Sensing findSensing = service.findSensingById(id);

    if (findSensing == null) throw new SensingNotFoundException(id);
    SensingResponseDto result = new SensingResponseDto(findSensing);

    return ResponseEntity.status(OK).body(result);
  }

  @PostMapping("auto")
  public ResponseEntity<String> createSensing(@RequestBody @Valid final SensingRegister registerDto) {
    System.out.println(registerDto.toString());
    service.register(registerDto);
    return ResponseEntity.status(CREATED).body("센싱 등록이 성공적으로 완료되었습니다.");
  }

  @Getter
  @NoArgsConstructor
  public static class SensingResponseDto {

    private UUID sensorId;
    private LocalDateTime sensingTime;
    private HashMap<String, String> sensingValue;
    private HashMap<String, String> environmentValue;

    public SensingResponseDto(Sensing sensing) {
      this.sensorId = sensing.getSensorId();
      this.sensingTime = sensing.getSensingTime();
      this.sensingValue = sensing.getSensingValue();
      this.environmentValue = sensing.getEnvironmentValue();
    }
  }

}
