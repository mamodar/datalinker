import {Resource} from './resource';

export class Project {
  id: number;
  creationTimestamp: string;
  projectName: string;
  description: string;
  rdmoId: number;
  owner: string;
  resources: Resource[];

}
