package de.rki.mamodar.rdmo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This DAO entity corresponding to the representation of a rdmo question in the database. No getters or setters as the
 * table is only used to created the {@link de.rki.mamodar.ValueRepository} view.
 *
 * @author Kyanoush Yahosseini
 */
@Entity
@Table(name = "rdmo_question")
public class RdmoQuestion {

  @Id
  @Column(name = "id", nullable = false)
  private Long rdmoId;
  private String uriPrefix;
  private String keywords;
  private String textEn;
  private String verboseNameEn;
  private String verboseNamePluralEn;
  private String textDe;
  private String verboseNameDe;
  private String verboseNamePluralDe;
  private Long attribute;

  /**
   * Instantiates a new empty rdmo question.
   */
  public RdmoQuestion() {
  }

  /**
   * Instantiates a new rdmo question from a rdmo question DTO
   *
   * @param rdmoQuestionDTO the rdmo question dto
   */
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

}
