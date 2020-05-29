/**
 * This class is used to preper a {@link Project} for transfer to edoc.
 * @author Kyanoush Yahosseini
 */
import {Project} from './project';

export class EdocTransfer {
  name: string;
  email: string;
  description: string;
  file: File;


  public fromProject(project: Project) {
    this.name = project.projectName;
    this.description = project.description;
    this.email = project.owner[0] + '@rki.de';
  }
}
