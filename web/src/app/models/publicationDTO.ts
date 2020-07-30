/**
 * This class is used to prepare a {@link Project} for transfer to edoc.
 * @author Kyanoush Yahosseini
 */
import {Project} from './project';
import {Value} from './value';

export class PublicationDTO {
  title: string;
  abstract: string;
  authors: string[] = [];
  description: string;
  issueDate: string;
  license: string;
  keywords: string[] = [];
  file: File;

  public fromProject(project: Project, values: Value[]): PublicationDTO {
    for (const value of values) {
      switch (value.questionText.toLocaleLowerCase()) {
        case 'titel':
          this.title = value.answerText;
          break;
        case 'kontaktperson':
          this.authors.push(value.answerText);
          break;
        case 'beschreibung':
          this.description = value.answerText;
          this.abstract = value.answerText;
          break;
        case 'publication date':
          this.issueDate = value.answerText;
          break;
        case 'lizenz \/ nutzungsbedingung':
          this.license = value.answerText;
          break;
        case 'schlagwort':
          this.keywords.push(value.answerText);
          break;
      }
    }
    if (!this.title) {
      this.title = project.projectName;
    }

    if (!this.description) {
      this.description = project.description;
    }
    if (!this.abstract) {
      this.description = project.description;
    }
    if (this.authors.length === 0) {
      project.owner.forEach(value => this.authors.push(value));
    }
    if (!this.issueDate) {
      const date = new Date();
      this.issueDate = date.toISOString().substring(0, 10);
    }

    if (this.keywords.length === 0) {
      this.keywords.push(this.title);
    }


    if (!this.license) {
      this.license = '(CC BY 3.0 DE) Namensnennung';
    }
    return this;
  }

}
