package de.rki.mamodar.rdmo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RdmoQuestionDTO {

  private Integer rdmoId;
  private String uriPrefix;
  private String keywords;
  private Long attribute;
  private String textEn;
  private String verboseNameEn;
  private String verboseNamePluralEn;
  private String textDe;
  private String verboseNameDe;
  private String verboseNamePluralDe;

  public Integer getRdmoId() {
    return rdmoId;
  }

  @JsonProperty("id")
  public void setRdmoId(Integer rdmoId) {
    this.rdmoId = rdmoId;
  }

  public String getUriPrefix() {
    return uriPrefix;
  }

  @JsonProperty("uri_prefix")
  public void setUriPrefix(String uriPrefix) {
    this.uriPrefix = uriPrefix;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public Long getAttribute() {
    return attribute;
  }

  public void setAttribute(Long attribute) {
    this.attribute = attribute;
  }

  public String getTextEn() {
    return textEn;
  }

  @JsonProperty("text_en")
  public void setTextEn(String textEn) {
    this.textEn = textEn;
  }

  public String getVerboseNameEn() {
    return verboseNameEn;
  }

  @JsonProperty("verbose_name_en")
  public void setVerboseNameEn(String verboseNameEn) {
    this.verboseNameEn = verboseNameEn;
  }

  public String getVerboseNamePluralEn() {
    return verboseNamePluralEn;
  }

  @JsonProperty("verbose_name_plural_en")
  public void setVerboseNamePluralEn(String verboseNamePluralEn) {
    this.verboseNamePluralEn = verboseNamePluralEn;
  }

  public String getTextDe() {
    return textDe;
  }

  @JsonProperty("text_de")
  public void setTextDe(String textDe) {
    this.textDe = textDe;
  }

  public String getVerboseNameDe() {
    return verboseNameDe;
  }

  @JsonProperty("verbose_name_de")
  public void setVerboseNameDe(String verboseNameDe) {
    this.verboseNameDe = verboseNameDe;
  }

  public String getVerboseNamePluralDe() {
    return verboseNamePluralDe;
  }

  @JsonProperty("verbose_name_plural_de")
  public void setVerboseNamePluralDe(String verboseNamePluralDe) {
    this.verboseNamePluralDe = verboseNamePluralDe;
  }
}
