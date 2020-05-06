
export class ResourceType {
  transferNumber: number;
  value: string;
  viewValue: string;
  pathDescriptor: string;

  constructor(value: string | number) {
    switch (value) {

      case 'SAN': case 'san-0': case 0:
        return {transferNumber: 0, value: 'SAN', viewValue: 'WissData S:', pathDescriptor: 'Pfad:'};
      case 'LOCAL': case 'local-1': case 1:
        return {transferNumber: 1, value: 'LOCAL', viewValue: 'Lokal', pathDescriptor: 'Pfad:'};
      case 'OPENBIS': case 'openbis-2': case 2:
        return  {transferNumber: 2, value: 'OPENBIS', viewValue: 'OpenBIS', pathDescriptor: 'ID:'};
      case 'GIT': case 'git-3': case 3:
        return {transferNumber: 3, value: 'GIT', viewValue: 'Git Repositorium', pathDescriptor: 'URL:'};
      case 'DOI': case 'doi-4': case 4:
        return {transferNumber: 4, value: 'DOI', viewValue: 'DOI Referenz', pathDescriptor: 'DOI:'};
      default:
        return({transferNumber: null, value: null, viewValue: null, pathDescriptor: null});

    }
}
}
