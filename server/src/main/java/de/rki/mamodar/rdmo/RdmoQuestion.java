package de.rki.mamodar.rdmo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rdmo_question")
public class RdmoQuestion {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  Long id;

  private Integer rdmoId;
  private String uriPrefix;
  private String keywords;
  private String textEn;
  private String verboseNameEn;
  private String verboseNamePluralEn;
  private String textDe;
  private String verboseNameDe;
  private String verboseNamePluralDe;
  private Long attribute;

  public RdmoQuestion() {
  }

  public RdmoQuestion(RdmoQuestionDTO rdmoQuestionDTO) {
    this.rdmoId = rdmoQuestionDTO.getRdmoId();
    this.uriPrefix = rdmoQuestionDTO.getUriPrefix();
    this.keywords = rdmoQuestionDTO.getKeywords();
    this.textEn = rdmoQuestionDTO.getTextEn();
    this.verboseNameEn = rdmoQuestionDTO.getVerboseNameEn();
    this.verboseNamePluralEn = rdmoQuestionDTO.getVerboseNamePluralEn();
    this.textDe = rdmoQuestionDTO.getTextDe();
    this.verboseNameDe = rdmoQuestionDTO.getVerboseNameDe();
    this.verboseNamePluralDe = rdmoQuestionDTO.getVerboseNamePluralDe();
    this.attribute = rdmoQuestionDTO.getAttribute();
  }

  public Long getId() {
    return id;
  }

  public Integer getRdmoId() {
    return rdmoId;
  }

  public void setRdmoId(Integer rdmoId) {
    this.rdmoId = rdmoId;
  }

  public String getUriPrefix() {
    return uriPrefix;
  }

  public void setUriPrefix(String uriPrefix) {
    this.uriPrefix = uriPrefix;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getTextEn() {
    return textEn;
  }

  public void setTextEn(String textEn) {
    this.textEn = textEn;
  }

  public String getVerboseNameEn() {
    return verboseNameEn;
  }

  public void setVerboseNameEn(String verboseNameEn) {
    this.verboseNameEn = verboseNameEn;
  }

  public String getVerboseNamePluralEn() {
    return verboseNamePluralEn;
  }

  public void setVerboseNamePluralEn(String verboseNamePluralEn) {
    this.verboseNamePluralEn = verboseNamePluralEn;
  }

  public String getTextDe() {
    return textDe;
  }

  public void setTextDe(String textDe) {
    this.textDe = textDe;
  }

  public String getVerboseNameDe() {
    return verboseNameDe;
  }

  public void setVerboseNameDe(String verboseNameDe) {
    this.verboseNameDe = verboseNameDe;
  }

  public String getVerboseNamePluralDe() {
    return verboseNamePluralDe;
  }

  public void setVerboseNamePluralDe(String verboseNamePluralDe) {
    this.verboseNamePluralDe = verboseNamePluralDe;
  }
}
