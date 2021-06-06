package io.wisoft.virtual.cnc.application;

import io.wisoft.virtual.cnc.ui.CncController.ActuatingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

import static io.wisoft.virtual.cnc.application.CncService.ActuatingResult.SUCCESS;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class CncService {

  @Value("${multiplicationHost}")
  private String API_GATEWAY_HOST;
  public final RestTemplate restTemplate;

  public void asyncActuating(ActuatingDto actuatingDto) {
    ActuatingResultDto resultDto = createActuatingDto(actuatingDto);
    String path = API_GATEWAY_HOST + "/event-actuatings/result";
    callActuatingResult(resultDto, path);
  }

  public void syncActuating(ActuatingDto actuatingDto) {
    ActuatingResultDto resultDto = createActuatingDto(actuatingDto);
    String path = API_GATEWAY_HOST + "/stereo-actuatings/result";
    callActuatingResult(resultDto, path);
  }

  private void callActuatingResult(ActuatingResultDto resultDto, String path) {
    URI uri = URI.create(path);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(APPLICATION_JSON);
    HttpEntity<ActuatingResultDto> entity = new HttpEntity<>(resultDto, headers);
    ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
    System.out.println(result);
  }

  private ActuatingResultDto createActuatingDto(ActuatingDto actuatingDto) {
    return ActuatingResultDto.builder()
        .actuatingId(actuatingDto.getActuatingId())
        .actuatorId(actuatingDto.getActuatorId())
        .actuatorName(actuatingDto.getActuatorName())
        .actuatingValue(actuatingDto.getActuatingValue())
        .actuatingResult(SUCCESS)
        .build();
  }

  @Builder
  @Getter
  public static class ActuatingResultDto {
    private UUID actuatingId;
    private UUID actuatorId;
    private String actuatorName;
    private String actuatingValue;
    private ActuatingResult actuatingResult;
    private String description;
  }

  public enum ActuatingResult {
    SUCCESS, FAIL, RUNNING
  }


}
