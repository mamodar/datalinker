import { ResourceType } from './resourceType';

export class Resource {
  id: number;

  path: string;
  location: ResourceType ;
  createdBy: string;
  createdTimestamp: string;
  updatedBy: string;
  updatedTimestamp: string;
  size: number;
  description: string;
  projectId: number;
  personal = true;
  thirdParty = false;
  archived = false;

}

