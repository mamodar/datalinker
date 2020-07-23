/**
 * This class represents the supported  types of resources.
 * It converts a between a string representation of a resource type and an object containing value, viewValue, and pathDescriptor.
 * @author Kyanoush Yahosseini
 *
 */
export class ResourceType {
  value: string;
  viewValue: string;
  pathDescriptor: string;

  constructor(value: string | number) {
    switch (value) {

      case 'SAN': case 'san-0': case 0:
        return { value: 'SAN', viewValue: 'WissData S:', pathDescriptor: 'Pfad:'};
      case 'OPENBIS': case 'openbis-2': case 2:
        return  { value: 'OPENBIS', viewValue: 'OpenBIS', pathDescriptor: 'ID:'};
      case 'GIT': case 'git-3': case 3:
        return { value: 'GIT', viewValue: 'Git Repositorium', pathDescriptor: 'URL:'};
      case 'DOI': case 'doi-4': case 4:
        return { value: 'DOI', viewValue: 'DOI Referenz', pathDescriptor: 'DOI:'};
      default:
        return({ value: null, viewValue: null, pathDescriptor: null});

    }
}
}
