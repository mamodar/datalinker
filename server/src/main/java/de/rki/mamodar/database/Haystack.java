package de.rki.mamodar.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.Immutable;

/**
 * This DAO entitiy corresponds to the concatination of all text fields of a project. It references a view, hence it is
 * immutable.
 */
@Entity
@Immutable
@Table(name = "search_view")
public class Haystack {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "rdmo_id")
  private Long rdmoId;

  @Column(name = "tsv", columnDefinition = "TEXT", nullable = false)
  private String text;
}
