import { ResourceType } from './resourceType';

export class Resource {
  id: number;
  date: Date;
  location: ResourceType;
  path: string;
  datatype: string;
}

