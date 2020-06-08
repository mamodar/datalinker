package de.rki.mamodar;


public class ValueSendDTO {

  Long id;
  Long projectId;
  Long attribute;
  String questionText;
  String answerText;

  public ValueSendDTO() {
  }

  public ValueSendDTO(Value value) {
    this.id = value.getId();
    this.projectId = value.getProjectId();
    this.attribute = value.getAttribute();
    this.questionText = value.getQuestionText();

    if (value.getOptionText() != null) {
      this.answerText = value.getOptionText();
    } else {
      if (!value.getAnswer().isBlank()) {
        this.answerText = value.getAnswer();
      }
    }
  }

  public Long getId() {
    return id;
  }

  public Long getProjectId() {
    return projectId;
  }

  public Long getAttribute() {
    return attribute;
  }

  public String getQuestionText() {
    return questionText;
  }

  public String getAnswerText() {
    return answerText;
  }
}
