package io.wisoft.actuating.infrastructure;

import io.wisoft.actuating.domain.Actuating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ActuatingRepository {

  private final EntityManager entityManager;

  public void save(final Actuating actuating) {
    entityManager.persist(actuating);
  }

  public List<Actuating> findActuatingList() {
    final String query = "SELECT a FROM actuating a";
    return entityManager.createQuery(query, Actuating.class)
        .getResultList();
  }

  public Actuating findActuatingById(UUID id) {
    final String query = "SELECT a FROM actuating a WHERE a.actuatingId=:id";
    return entityManager.createQuery(query, Actuating.class)
        .setParameter("id", id)
        .getResultList()
        .stream()
        .findFirst()
        .orElse(null);

  }
}
