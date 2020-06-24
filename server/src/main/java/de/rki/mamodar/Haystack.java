package de.rki.mamodar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.Immutable;

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
