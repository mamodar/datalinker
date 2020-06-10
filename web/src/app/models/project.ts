/**
 * This class represents a project as received by the API calls.
 * @author Kyanoush Yahosseini
 */
export class Project {
  id: number;
  creationTimestamp: string;
  projectName: string;
  description: string;
  rdmoId: number;
  owner: string[];
}
