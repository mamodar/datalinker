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
      case 'SAN_OU':
        return {value: 'SAN_OU', viewValue: 'Gruppenlaufwerk (S:\\OE)', pathDescriptor: 'Pfad:'};
      case 'SAN_PROJECT':
        return {value: 'SAN_PROJECT', viewValue: 'Projektlaufwerk (S:\\Projekte)', pathDescriptor: 'Pfad:'};
      case 'SAN_DATA':
        return {value: 'SAN_DATA', viewValue: 'WissDaten (S:\\Wissdaten)', pathDescriptor: 'Pfad:'};
      case 'OPENBIS':
        return {value: 'OPENBIS', viewValue: 'OpenBIS', pathDescriptor: 'ID:'};
      case 'GIT':
        return {value: 'GIT', viewValue: 'Git Repositorium', pathDescriptor: 'URL:'};
      case 'DOI':
        return {value: 'DOI', viewValue: 'DOI Referenz', pathDescriptor: 'DOI:'};
      case 'DMS':
        return {value: 'DMS', viewValue: 'DMS/VBS', pathDescriptor: 'Adresse:'};
      default:
        return ({value: null, viewValue: null, pathDescriptor: null});

    }
  }
}
