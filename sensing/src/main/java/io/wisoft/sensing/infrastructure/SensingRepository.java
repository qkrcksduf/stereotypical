package io.wisoft.sensing.infrastructure;

import io.wisoft.sensing.domain.Sensing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SensingRepository {

  private final EntityManager entityManager;

  public void save(Sensing sensing) {
    entityManager.persist(sensing);
  }

  public List<Sensing> findSensingList() {
    final String query = "SELECT s From sensing s";
    return entityManager.createQuery(query, Sensing.class)
        .getResultList();
  }

  public Sensing findSensingById(UUID id) {
    final String query = "SELECT s FROM sensing s WHERE s.sensingId=:id";
    return entityManager.createQuery(query, Sensing.class)
        .setParameter("id", id)
        .getResultList()
        .stream()
        .findFirst()
        .orElse(null);
  }
}
