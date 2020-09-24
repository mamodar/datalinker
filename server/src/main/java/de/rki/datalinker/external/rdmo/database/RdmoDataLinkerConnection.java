package de.rki.datalinker.external.rdmo.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rdmo_datalinker_connection")
public class RdmoDataLinkerConnection {

  @Id
  @Column(name = "attribute", nullable = false)
  private Long attribute;
  @Column(name = "order")
  private Long order;
  @Column(name = "is_filter")
  private Boolean isFilter;
  @Column(name = "is_collection")
  private Boolean isCollection;

  public Long getAttribute() {
    return attribute;
  }

  public Long getOrder() {
    return order;
  }

  public Boolean getFilter() {
    return isFilter;
  }

  public Boolean getCollection() {
    return isCollection;
  }
}
