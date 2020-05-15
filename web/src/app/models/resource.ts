import { ResourceType } from './resourceType';
import {ResourcePath} from './resourcePath';

export class Resource {
  id: number;

  path: ResourcePath;
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

