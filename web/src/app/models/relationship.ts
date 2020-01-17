import {Project} from './project';
import {Resource} from './resource';

export class Relationship {
  id: number;
  project: Project;
  resource: Resource;
  date: Date;
}
