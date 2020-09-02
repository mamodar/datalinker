package de.rki.datalinker.external.rdmo.database;

import de.rki.datalinker.database.ValueRepository;
import de.rki.datalinker.dto.RdmoQuestionDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This DAO entity corresponding to the representation of a rdmo question in the database. No getters or setters as the
 * table is only used to created the {@link ValueRepository} view.
 *
 * @author Kyanoush Yahosseini
 */
@Entity
@Table(name = "rdmo_question")
public class RdmoQuestion {

  @Id
  @Column(name = "id", nullable = false)
  private Long rdmoId;
  @Column
  private String uriPrefix;
  @Column
  private String keywords;
  @Column(length = 1000)
  private String textEn;
  @Column
  private String verboseNameEn;
  @Column
  private String verboseNamePluralEn;
  @Column(length = 1000)
  private String textDe;
  @Column
  private String verboseNameDe;
  @Column
  private String verboseNamePluralDe;
  @Column
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

  public Long getAttribute() {
    return attribute;
  }
}
