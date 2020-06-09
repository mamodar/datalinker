package de.rki.mamodar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This DAO entity corresponds to the representation of a value in the database. Values are only part of a view (not a
 * table), hence this class only provides getters and no setters.
 *
 * @author Kyanoush Yahosseini
 */
@Entity
@Table(name = "question_answer_view")
public class Value {

  /**
   * The Id.
   */
  @Id
  Long id;
  /**
   * The Project.
   */
  @ManyToOne(targetEntity = Project.class)
  Project project;
  /**
   * The Attribute.
   */
  @Column(name = "attribute")
  Long attribute;
  /**
   * The Question text.
   */
  @Column(name = "question_text")
  String questionText;
  /**
   * The Answer.
   */
  @Column(name = "answer")
  String answer;
  /**
   * The Option text.
   */
  @Column(name = "option_text")
  String optionText;

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets project id.
   *
   * @return the project id
   */
  public Project getProject() {
    return project;
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
}
