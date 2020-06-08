package de.rki.mamodar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question_answer_view")
public class Value {

  @Id
  Long id;
  @Column(name = "project_id")
  Long projectId;
  @Column(name = "attribute")
  Long attribute;
  @Column(name = "question_text")
  String questionText;
  @Column(name = "answer")
  String answer;
  @Column(name = "option_text")
  String optionText;

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

  public String getAnswer() {
    return answer;
  }

  public String getOptionText() {
    return optionText;
  }
}
