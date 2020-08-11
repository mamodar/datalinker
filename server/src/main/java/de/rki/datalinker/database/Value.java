package de.rki.datalinker.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.Immutable;

/**
 * This DAO entity corresponds to the representation of a value in the database. Values are only part of a view (not a
 * table), hence this class only provides getters and no setters.
 *
 * @author Kyanoush Yahosseini
 */
@Entity
@Immutable
@Table(name = "question_answer_view")
public class Value {

  @Id
  @Column(name = "id")
  private Long id;
  @Column(name = "project_rdmo_id")
  private long projectRdmoId;
  @Column(name = "attribute")
  private Long attribute;
  @Column(name = "question_text")
  private String questionText;
  @Column(name = "answer")
  private String answer;
  @Column(name = "unit")
  private String unit;
  @Column(name = "option_text")
  private String optionText;

  @Column(name = "order")
  private Integer order;
  @Column(name = "is_filter")
  private Boolean isFilter;
  @Column(name = "is_collection")
  private Boolean isCollection;

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the corresponding project rdmo id.
   *
   * @return the project rdmo id
   */
  public long getProjectRdmoId() {
    return projectRdmoId;
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
   * Gets question text.
   *
   * @return the question text
   */
  public String getQuestionText() {
    return questionText;
  }

  /**
   * Gets answer.
   *
   * @return the answer
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * Gets option text.
   *
   * @return the option text
   */
  public String getOptionText() {
    return optionText;
  }

  /**
   * Gets unit.
   *
   * @return the unit
   */
  public String getUnit() {
    return unit;
  }

  public Integer getOrder() {
    return order;
  }

  public Boolean getFilter() {
    return isFilter;
  }

  public Boolean getCollection() {
    return isCollection;
  }
}
