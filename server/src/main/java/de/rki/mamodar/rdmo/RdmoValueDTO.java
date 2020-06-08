package de.rki.mamodar.rdmo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RdmoValueDTO {

  private Long rdmoId;
  private Integer setIndex;
  private Integer collectionIndex;
  private String text;
  private String valueType;
  private String unit;

  // option id
  private Long option;
  // project id
  private Long project;
  //pseudo question id
  private Long attribute;

  public Long getRdmoId() {
    return rdmoId;
  }

  @JsonProperty("id")
  public void setRdmoId(Long rdmoId) {
    this.rdmoId = rdmoId;
  }


  public Long getAttribute() {
    return attribute;
  }

  public void setAttribute(Long attribute) {
    this.attribute = attribute;
  }

  public Integer getSetIndex() {
    return setIndex;
  }

  @JsonProperty("set_index")
  public void setSetIndex(Integer setIndex) {
    this.setIndex = setIndex;
  }

  public Integer getCollectionIndex() {
    return collectionIndex;
  }

  @JsonProperty("collection_index")
  public void setCollectionIndex(Integer collectionIndex) {
    this.collectionIndex = collectionIndex;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getOption() {
    return option;
  }

  public void setOption(Long option) {
    this.option = option;
  }

  public String getValueType() {
    return valueType;
  }

  @JsonProperty("value_type")
  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Long getProject() {
    return project;
  }

  public void setProject(Long project) {
    this.project = project;
  }
}
