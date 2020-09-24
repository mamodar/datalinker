package de.rki.datalinker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class provides a value DTO for receiving data from the rdmo API.
 *
 * @author Kyanoush Yahosseini
 */
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

  //snapshot id
  private Long snapshot;

  /**
   * Gets rdmo id.
   *
   * @return the rdmo id
   */
  public Long getRdmoId() {
    return rdmoId;
  }

  /**
   * Sets rdmo id.
   *
   * @param rdmoId the rdmo id
   */
  @JsonProperty("id")
  public void setRdmoId(Long rdmoId) {
    this.rdmoId = rdmoId;
  }


  /**
   * Gets attribute.
   *
   * @return the attribute
   */
  public Long getAttribute() {
    return attribute;
  }

  /**
   * Sets attribute.
   *
   * @param attribute the attribute
   */
  public void setAttribute(Long attribute) {
    this.attribute = attribute;
  }

  /**
   * Gets set index.
   *
   * @return the set index
   */
  public Integer getSetIndex() {
    return setIndex;
  }

  /**
   * Sets set index.
   *
   * @param setIndex the set index
   */
  @JsonProperty("set_index")
  public void setSetIndex(Integer setIndex) {
    this.setIndex = setIndex;
  }

  /**
   * Gets collection index.
   *
   * @return the collection index
   */
  public Integer getCollectionIndex() {
    return collectionIndex;
  }

  /**
   * Sets collection index.
   *
   * @param collectionIndex the collection index
   */
  @JsonProperty("collection_index")
  public void setCollectionIndex(Integer collectionIndex) {
    this.collectionIndex = collectionIndex;
  }

  /**
   * Gets text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Sets text.
   *
   * @param text the text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Gets option.
   *
   * @return the option
   */
  public Long getOption() {
    return option;
  }

  /**
   * Sets option.
   *
   * @param option the option
   */
  public void setOption(Long option) {
    this.option = option;
  }

  /**
   * Gets value type.
   *
   * @return the value type
   */
  public String getValueType() {
    return valueType;
  }

  /**
   * Sets value type.
   *
   * @param valueType the value type
   */
  @JsonProperty("value_type")
  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  /**
   * Gets unit.
   *
   * @return the unit
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Sets unit.
   *
   * @param unit the unit
   */
  public void setUnit(String unit) {
    this.unit = unit;
  }

  /**
   * Gets project.
   *
   * @return the project
   */
  public Long getProject() {
    return project;
  }

  /**
   * Sets project.
   *
   * @param project the project
   */
  public void setProject(Long project) {
    this.project = project;
  }

  public Long getSnapshot() {
    return snapshot;
  }
}
