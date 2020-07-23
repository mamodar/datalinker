import {ResourceType} from './resourceType';
import {ResourcePath} from './resourcePath';

/**
 * This class represents a resource as used in the application.
 * It is close to a resource as received by the API calls, however the fields path and location have to be converted from strings to objects by calls to {@link ResourcePath} and {@link ResourceType}.
 * @author Kyanoush Yahosseini
 */
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
  archived = false;


}

