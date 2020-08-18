package de.rki.datalinker.dto;


import de.rki.datalinker.api.ProjectController;
import de.rki.datalinker.database.Value;

/**
 * This class provides a DTO for sending {@link Value}s as part of {@link ProjectController}.
 *
 * @author Kyanoush Yahosseini
 */
public class ValueDTO {

  private Long id;
  private Long projectId;
  private Long attribute;
  private String questionText;
  private String answerText;
  private Boolean isFilter;

  /**
   * Instantiates a new Value dto.
   */
  public ValueDTO() {
  }

  /**
   * Instantiates a new Value DTO from a value DAO.
   *
   * @param value the value DAO
   */
  public ValueDTO(Value value) {
    this.id = value.getId();
    this.projectId = value.getProjectRdmoId();
    this.attribute = value.getAttribute();
    this.questionText = value.getQuestionText();
    this.isFilter = value.getFilter();
    if (value.getOptionText() != null) {
      this.answerText = value.getOptionText();
    } else {
      if (!value.getAnswer().isBlank()) {
        this.answerText = value.getAnswer();
      } else {
        this.answerText = "";
      }
    }
    if (value.getUnit() != null) {
      this.answerText = this.answerText.concat(value.getUnit());
    }

  }

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
  public Long getProjectId() {
    return projectId;
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
   * Gets answer text.
   *
   * @return the answer text
   */
  public String getAnswerText() {
    return answerText;
  }

  /**
   * Gets filter.
   *
   * @return the filter
   */
  public Boolean getFilter() {
    return isFilter;
  }
}
