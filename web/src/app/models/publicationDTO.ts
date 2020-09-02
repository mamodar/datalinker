/**
 * This class is used to prepare a {@link Project} for transfer to edoc.
 * @author Kyanoush Yahosseini
 */
import {Project} from './project';
import {Value} from './value';
import {Author} from './author';

export class PublicationDTO {
  title: string;
  abstract: string;
  authors: Author[] = [];
  description: string;
  issueDate: string;
  license: string;
  keywords: string[] = [];
  files: File[] = [];
  private tmpAuthorNames: string[] = [];
  public fromProject(project: Project, values: Value[]): PublicationDTO {
    for (const value of values) {
      console.log('fromProject:' + value.answerText);
      switch (value.questionText.toLocaleLowerCase()) {
        case 'titel':
          this.title = value.answerText;
          break;
        case 'kontaktperson':
          value.answerText.split(';').forEach(value1 => this.tmpAuthorNames.push(value1));
          break;
        case 'projektleiter\*in':
          value.answerText.split(';').forEach(value1 => this.tmpAuthorNames.push(value1));
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
          value.answerText.split(';').forEach(value1 => this.keywords.push(value1));
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
    if (this.tmpAuthorNames.length === 0) {
      project.owner.forEach(value => this.tmpAuthorNames.push(value));
    }
    if (!this.issueDate) {
      const date = new Date();
      this.issueDate = date.toISOString().substring(0, 10);
    }

    if (this.keywords.length === 0) {
      this.keywords.push(this.title);
    }
    // delete duplication
    this.tmpAuthorNames = [...new Set(this.tmpAuthorNames)];
    this.tmpAuthorNames.forEach(value => this.authors.push(new Author(value, 'Robert Koch-Institut')));

    if (!this.license) {
      this.license = '(CC BY 3.0 DE) Namensnennung';
    }
    return this;
  }

}
