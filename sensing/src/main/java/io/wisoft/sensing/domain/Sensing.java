package io.wisoft.sensing.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity(name = "sensing")
@TypeDef(
    name = "jsonb",
    typeClass = JsonBinaryType.class
)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Sensing {

  @Id
  @Column(name = "sensing_id")
  private UUID sensingId;

  @Column(name = "sensor_id")
  private UUID sensorId;

  @CreationTimestamp
  @Column(name = "sensing_time")
  private LocalDateTime sensingTime;

  @Type(type = "jsonb")
  @Column(name = "sensing_value", columnDefinition = "jsonb")
  private HashMap<String, String> sensingValue;

  @Type(type = "jsonb")
  @Column(name = "environment_value", columnDefinition = "jsonb")
  private HashMap<String, String> environmentValue;

}
