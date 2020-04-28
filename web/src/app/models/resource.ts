import { ResourceType } from './resourceType';

export class Resource {
  id: number;
  date: Date;
  location: ResourceType ;
  path: string;
  description: string;
  personal: boolean;
  thirdParty: boolean;
  size: number;
  archived: boolean;

  constructor() {

    return {id: null, date: null,
      location: null, path: null,
      description: null, personal: true,
      thirdParty: false, archived: false,
      size: 1};
  }


}

