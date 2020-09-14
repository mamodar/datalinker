/**
 * This class represents the supported  types of resources.
 * It converts a between a string representation of a resource type and an object containing value, viewValue, and pathDescriptor.
 * @author Kyanoush Yahosseini
 *
 */
export class ResourceLocation {
  value: string;
  viewValue: string;
  pathDescriptor: string;
  pathDefault: string;
  constructor(value: string | number) {
    switch (value) {
      case 'SAN_OU':
        return {value: 'SAN_OU', viewValue: 'Gruppenlaufwerk (S:\\OE)', pathDescriptor: 'Pfad:', pathDefault: 'S:\\OE\\'};
      case 'SAN_PROJECT':
        return {value: 'SAN_PROJECT', viewValue: 'Projektlaufwerk (S:\\Projekte)', pathDescriptor: 'Pfad:', pathDefault: 'S:\\Projekte\\'};
      case 'SAN_DATA':
        return {value: 'SAN_DATA', viewValue: 'WissDaten (S:\\Wissdaten)', pathDescriptor: 'Pfad:', pathDefault: 'S:\\Wissdaten\\'};
      case 'OPENBIS':
        return {value: 'OPENBIS', viewValue: 'OpenBIS', pathDescriptor: 'ID:', pathDefault: ''};
      case 'GIT':
        return {value: 'GIT', viewValue: 'Git Repositorium', pathDescriptor: 'URL:', pathDefault: 'https://'};
      case 'URL':
        return {value: 'URL', viewValue: 'Website/URL', pathDescriptor: 'URL:', pathDefault: 'https://'};
      case 'DOI':
        return {value: 'DOI', viewValue: 'DOI Referenz', pathDescriptor: 'DOI:', pathDefault: ''};
      case 'DMS':
        return {value: 'DMS', viewValue: 'DMS/VBS', pathDescriptor: 'Adresse:', pathDefault: 'COO.'};
      default:
        return ({value: null, viewValue: null, pathDescriptor: null, pathDefault: null});

    }
  }
}
