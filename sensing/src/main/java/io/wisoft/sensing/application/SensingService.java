package io.wisoft.sensing.application;

import io.wisoft.sensing.domain.Sensing;
import io.wisoft.sensing.exception.ActuatorNotFoundException;
import io.wisoft.sensing.exception.SensorNotFoundException;
import io.wisoft.sensing.infrastructure.SensingRepository;
import io.wisoft.sensing.ui.SensingDto.SensingRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class SensingService {

  @Value("${multiplicationHost}")
  private String API_GATEWAY_HOST;
  private final RestTemplate restTemplate;
  private final WebClient webClient;
  private final SensingRepository repository;

  public List<Sensing> findSensingList() {
    return repository.findSensingList();
  }

  public Sensing findSensingById(UUID id) {
    return repository.findSensingById(id);
  }

  public void register(SensingRegister registerDto) {
    checkExistSensor(registerDto.getSensorId());
    Map<String, Object> actuator = checkExistActuator(registerDto.getActuatorId());
    Sensing sensing = new Sensing(
        UUID.randomUUID(),
        registerDto.getSensorId(),
        LocalDateTime.now(),
        registerDto.getSensingValue(),
        registerDto.getEnvironmentValue());
    repository.save(sensing);
    actuating(createActuatingDto(actuator, registerDto));
  }

  private ActuatingDto createActuatingDto(Map<String, Object> actuator, SensingRegister register) {
    return ActuatingDto.builder()
        .actuatingId(UUID.randomUUID())
        .actuatorId(UUID.fromString((String) actuator.get("id")))
        .actuatorName((String) actuator.get("name"))
        .actuatingValue(register.getActuatingValue())
        .protocol("MQTT")
        .build();
  }

  private void checkExistSensor(final UUID sensorId) {
    try {
      webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/sensors/{id}")
              .build(sensorId))
          .retrieve();
    } catch (HttpClientErrorException e) {
      throw new SensorNotFoundException(String.valueOf(sensorId));
    }
  }

  private Map<String, Object> checkExistActuator(final UUID actuatorId) {
    try {
      final ResponseEntity<Object> response = restTemplate.getForEntity(API_GATEWAY_HOST + "/actuators/{id}", Object.class, actuatorId);
      Map<String, Object> actuator = (Map<String, Object>) response.getBody();

      if (actuator != null && actuator.isEmpty()) {
        throw new ActuatorNotFoundException(String.valueOf(actuatorId));
      }
      return actuator;
    } catch (HttpClientErrorException e) {
      throw new ActuatorNotFoundException(String.valueOf(actuatorId));
    }
  }

  private void actuating(ActuatingDto responseDto) {
    final String uri = API_GATEWAY_HOST + "/stereo-actuatings/{id}";
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ActuatingDto> entity = new HttpEntity<>(responseDto, headers);

    System.out.println("entity: " + entity.getBody().toString());
    try {
      ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class, String.valueOf(responseDto.getActuatorId()));
      System.out.println(result);
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
    }

  }

}
