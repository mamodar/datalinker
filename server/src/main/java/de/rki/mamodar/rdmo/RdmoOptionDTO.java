package de.rki.mamodar.rdmo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RdmoOptionDTO {

  private Integer rdmoId;
  private Integer optionset;
  private String uriPrefix;
  private String key;
  private String text;
  private Boolean additionalInput;
  private String textEn;
  private String textDe;

  public Integer getRdmoId() {
    return rdmoId;
  }

  @JsonProperty("id")
  public void setRdmoId(Integer rdmoId) {
    this.rdmoId = rdmoId;
  }

  public Integer getOptionset() {
    return optionset;
  }

  public void setOptionset(Integer optionset) {
    this.optionset = optionset;
  }

  public String getUriPrefix() {
    return uriPrefix;
  }

  @JsonProperty("uri_prefix")
  public void setUriPrefix(String uriPrefix) {
    this.uriPrefix = uriPrefix;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Boolean getAdditionalInput() {
    return additionalInput;
  }

  @JsonProperty("additional_input")
  public void setAdditionalInput(Boolean additionalInput) {
    this.additionalInput = additionalInput;
  }

  public String getTextEn() {
    return textEn;
  }

  @JsonProperty("text_en")
  public void setTextEn(String textEn) {
    this.textEn = textEn;
  }

  public String getTextDe() {
    return textDe;
  }

  @JsonProperty("text_de")
  public void setTextDe(String textDe) {
    this.textDe = textDe;
  }
}
