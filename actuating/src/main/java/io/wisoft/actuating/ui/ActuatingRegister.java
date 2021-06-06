package io.wisoft.actuating.ui;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class ActuatingRegister {

  @NotNull
  private UUID actuatingId;

  @NotNull
  private UUID actuatorId;

  @NotNull
  private String actuatorName;

  @NotNull
  private String actuatingValue;

  @NotNull
  private ActuatingResult actuatingResult;

  public static enum ActuatingResult {
    SUCCESS, FAIL, RUNNING
  }

}
