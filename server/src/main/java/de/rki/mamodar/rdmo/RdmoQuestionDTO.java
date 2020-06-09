package de.rki.mamodar.rdmo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class provides a question DTO for receiving data from the rdmo API.
 *
 * @author Kyanoush Yahosseini
 */
public class RdmoQuestionDTO {

  private Long rdmoId;
  private String uriPrefix;
  private String keywords;
  private Long attribute;
  private String textEn;
  private String verboseNameEn;
  private String verboseNamePluralEn;
  private String textDe;
  private String verboseNameDe;
  private String verboseNamePluralDe;

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
   * Gets keywords.
   *
   * @return the keywords
   */
  public String getKeywords() {
    return keywords;
  }

  /**
   * Sets keywords.
   *
   * @param keywords the keywords
   */
  public void setKeywords(String keywords) {
    this.keywords = keywords;
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
   * Gets verbose name en.
   *
   * @return the verbose name en
   */
  public String getVerboseNameEn() {
    return verboseNameEn;
  }

  /**
   * Sets verbose name en.
   *
   * @param verboseNameEn the verbose name en
   */
  @JsonProperty("verbose_name_en")
  public void setVerboseNameEn(String verboseNameEn) {
    this.verboseNameEn = verboseNameEn;
  }

  /**
   * Gets verbose name plural en.
   *
   * @return the verbose name plural en
   */
  public String getVerboseNamePluralEn() {
    return verboseNamePluralEn;
  }

  /**
   * Sets verbose name plural en.
   *
   * @param verboseNamePluralEn the verbose name plural en
   */
  @JsonProperty("verbose_name_plural_en")
  public void setVerboseNamePluralEn(String verboseNamePluralEn) {
    this.verboseNamePluralEn = verboseNamePluralEn;
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

  /**
   * Gets verbose name de.
   *
   * @return the verbose name de
   */
  public String getVerboseNameDe() {
    return verboseNameDe;
  }

  /**
   * Sets verbose name de.
   *
   * @param verboseNameDe the verbose name de
   */
  @JsonProperty("verbose_name_de")
  public void setVerboseNameDe(String verboseNameDe) {
    this.verboseNameDe = verboseNameDe;
  }

  /**
   * Gets verbose name plural de.
   *
   * @return the verbose name plural de
   */
  public String getVerboseNamePluralDe() {
    return verboseNamePluralDe;
  }

  /**
   * Sets verbose name plural de.
   *
   * @param verboseNamePluralDe the verbose name plural de
   */
  @JsonProperty("verbose_name_plural_de")
  public void setVerboseNamePluralDe(String verboseNamePluralDe) {
    this.verboseNamePluralDe = verboseNamePluralDe;
  }
}
