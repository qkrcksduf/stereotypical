package io.wisoft.actuating.application;

import io.wisoft.actuating.domain.Actuating;
import io.wisoft.actuating.exception.ActuatorNotFoundException;
import io.wisoft.actuating.exception.CncNotFoundException;
import io.wisoft.actuating.infrastructure.ActuatingRepository;
import io.wisoft.actuating.ui.ActuatingController.ActuatingDto;
import io.wisoft.actuating.ui.ActuatingRegister;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ActuatingService {

  @Value("${multiplicationHost}")
  private String API_GATEWAY_HOST;
  private final RestTemplate restTemplate;
  private final ActuatingRepository repository;

  public List<Actuating> findActuatingList() {
    return repository.findActuatingList();
  }

  public void actuating(String actuatorId, ActuatingDto actuatingDto) {
    String uri = API_GATEWAY_HOST + "/virtual-cncs/sync-actuating";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ActuatingDto> entity = new HttpEntity<>(actuatingDto, headers);
    System.out.println("entity: " + entity.getBody().toString());
    try {
      ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
      System.out.println(result);
    } catch (HttpClientErrorException e) {
      throw new CncNotFoundException(actuatorId);
    }

  }

  public void register(ActuatingRegister actuatingRegister) {
    checkExistActuator(actuatingRegister.getActuatorId());

    Actuating actuating = Actuating.builder()
        .actuatingId(actuatingRegister.getActuatingId())
        .actuatorId(actuatingRegister.getActuatorId())
        .actuatorName(actuatingRegister.getActuatorName())
        .actuatingTime(LocalDateTime.now())
        .actuatingValue(actuatingRegister.getActuatingValue())
        .result(actuatingRegister.getActuatingResult())
        .build();
    repository.save(actuating);
  }

  private void checkExistActuator(final UUID actuatorId) {
    try {
      restTemplate.getForEntity(API_GATEWAY_HOST + "/actuators/{id}", Object.class, actuatorId);
    } catch (HttpClientErrorException e) {
      throw new ActuatorNotFoundException(String.valueOf(actuatorId));
    }
  }

  public Actuating findActuatingById(UUID id) {
    return repository.findActuatingById(id);
  }
}
