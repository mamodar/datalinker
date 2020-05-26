/**
 * This class is used to preper a {@link Project} for transfer to edoc.
 * @author Kyanoush Yahosseini
 */
import {Project} from './project';

export class EdocTransfer {
  name: string;
  description: string;
  person: string;

  public fromProject(project: Project) {
    this.name = project.projectName;
    this.description = project.description;
    this.person = project.owner[0];
  }
}
