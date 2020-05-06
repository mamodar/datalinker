import { ResourceType } from './resourceType';

export class Resource {
  id: number;
  userName: string;
  path: string;
  location: ResourceType ;
  creationTimestamp: string;
  size: number;
  description: string;
  projectId: number;
  personal = true;
  thirdParty = false;
  archived = false;

}

