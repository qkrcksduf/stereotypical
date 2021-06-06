package io.wisoft.sensing.application;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
@ToString
public class ActuatingDto {

  private UUID actuatingId;
  private UUID actuatorId;
  private String actuatorName;
  private String actuatingValue;
  private String protocol;

}
