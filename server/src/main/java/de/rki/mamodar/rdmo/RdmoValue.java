package de.rki.mamodar.rdmo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rdmo_value")
public class RdmoValue {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  Long id;

  private Long rdmoId;
  private Integer setIndex;
  private Integer collectionIndex;
  private String text;
  private String valueType;
  private String unit;

  private Long option;
  private Long project;
  private Long attribute;

  public RdmoValue() {
  }

  public RdmoValue(RdmoValueDTO rdmoValueDTO) {
    this.rdmoId = rdmoValueDTO.getRdmoId();
    this.setIndex = rdmoValueDTO.getSetIndex();
    this.collectionIndex = rdmoValueDTO.getCollectionIndex();
    this.text = rdmoValueDTO.getText();
    this.valueType = rdmoValueDTO.getValueType();
    this.unit = rdmoValueDTO.getUnit();
    this.option = rdmoValueDTO.getOption();
    this.project = rdmoValueDTO.getProject();
    this.attribute = rdmoValueDTO.getAttribute();
  }
}
