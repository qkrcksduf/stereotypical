package io.wisoft.actuating.domain;

import io.wisoft.actuating.ui.ActuatingRegister.ActuatingResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity(name = "actuating")
@Table
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class Actuating {

  @Id
  @Column(name = "actuating_id")
  private UUID actuatingId;

  @Column(name = "actuator_id", nullable = false)
  private UUID actuatorId;

  @Column(name = "actuator_name", nullable = false)
  private String actuatorName;

  @CreationTimestamp
  @Column(name = "actuating_time", nullable = false)
  private LocalDateTime actuatingTime;

  @Column(name = "actuating_value", nullable = false)
  private String actuatingValue;

  @Enumerated(STRING)
  @Column(name = "result")
  private ActuatingResult result;

}
