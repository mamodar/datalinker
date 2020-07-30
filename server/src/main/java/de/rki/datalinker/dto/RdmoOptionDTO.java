package de.rki.datalinker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class provides a option DTO for receiving data from the rdmo API.
 *
 * @author Kyanoush Yahosseini
 */
public class RdmoOptionDTO {

  private Long rdmoId;
  private Integer optionset;
  private String uriPrefix;
  private String key;
  private String text;
  private Boolean additionalInput;
  private String textEn;
  private String textDe;

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
   * Gets optionset.
   *
   * @return the optionset
   */
  public Integer getOptionset() {
    return optionset;
  }

  /**
   * Sets optionset.
   *
   * @param optionset the optionset
   */
  public void setOptionset(Integer optionset) {
    this.optionset = optionset;
  }

  /**
   * Gets uri prefix.
   *
   * @return the uri prefix
   */
  public String getUriPrefix() {
    return uriPrefix;
  }

  /**
   * Sets uri prefix.
   *
   * @param uriPrefix the uri prefix
   */
  @JsonProperty("uri_prefix")
  public void setUriPrefix(String uriPrefix) {
    this.uriPrefix = uriPrefix;
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Sets key.
   *
   * @param key the key
   */
  public void setKey(String key) {
    this.key = key;
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
   * Gets additional input.
   *
   * @return the additional input
   */
  public Boolean getAdditionalInput() {
    return additionalInput;
  }

  /**
   * Sets additional input.
   *
   * @param additionalInput the additional input
   */
  @JsonProperty("additional_input")
  public void setAdditionalInput(Boolean additionalInput) {
    this.additionalInput = additionalInput;
  }

  /**
   * Gets text en.
   *
   * @return the text en
   */
  public String getTextEn() {
    return textEn;
  }

  /**
   * Sets text en.
   *
   * @param textEn the text en
   */
  @JsonProperty("text_en")
  public void setTextEn(String textEn) {
    this.textEn = textEn;
  }

  /**
   * Gets text de.
   *
   * @return the text de
   */
  public String getTextDe() {
    return textDe;
  }

  /**
   * Sets text de.
   *
   * @param textDe the text de
   */
  @JsonProperty("text_de")
  public void setTextDe(String textDe) {
    this.textDe = textDe;
  }
}
