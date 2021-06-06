package io.wisoft.sensing.ui;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.UUID;

public class SensingDto {

  @Getter
  @Setter
  @ToString
  public static class SensingRegister {

    @NotNull
    private UUID sensorId;

    @NotNull
    private UUID actuatorId;

    @NotNull
    @Size(min = 2, max = 50)
    private String actuatingValue;

    @NotNull
    private HashMap<String, String> sensingValue;

    @NotNull
    private HashMap<String, String> environmentValue;
  }

}
