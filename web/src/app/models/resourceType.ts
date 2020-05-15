
export class ResourceType {
  value: string;
  viewValue: string;
  pathDescriptor: string;

  constructor(value: string | number) {
    switch (value) {

      case 'SAN': case 'san-0': case 0:
        return { value: 'SAN', viewValue: 'WissData S:', pathDescriptor: 'Pfad:'};
      case 'LOCAL': case 'local-1': case 1:
        return { value: 'LOCAL', viewValue: 'Lokal', pathDescriptor: 'Pfad:'};
      case 'OPENBIS': case 'openbis-2': case 2:
        return  { value: 'OPENBIS', viewValue: 'OpenBIS', pathDescriptor: 'ID:'};
      case 'GIT': case 'git-3': case 3:
        return { value: 'GIT', viewValue: 'Git Repositorium', pathDescriptor: 'URL:'};
      case 'DOI': case 'doi-4': case 4:
        return { value: 'DOI', viewValue: 'DOI Referenz', pathDescriptor: 'DOI:'};
      case 'PRIV': case 'priv-5': case 5:
        return { value: 'PRIV', viewValue: 'Private Ablage P:', pathDescriptor: 'Pfad:'};
      case 'CLOUD': case 'cloud-6': case 6:
        return { value: 'CLOUD', viewValue: 'Cloud Storage', pathDescriptor: 'Name:'};
      default:
        return({ value: null, viewValue: null, pathDescriptor: null});

    }
}
}
