
export class ResourceType {
  transferNumber: number;
  value: string;
  viewValue: string;
  pathDescriptor: string;
  constructor(value: string) {
    console.log(value);
    switch (value) {
      case 'SAN':
        return {transferNumber: 0, value: 'san-0', viewValue: 'lokal', pathDescriptor: 'Pfad:'};
      case 'LOCAL':
        return {transferNumber: 1, value: 'local-1', viewValue: 'WissData S:', pathDescriptor: 'Pfad:'};
      case 'OPENBIS':
        return  {transferNumber: 2, value: 'openbis-2', viewValue: 'OpenBIS', pathDescriptor: 'ID:'};
      case 'GIT':
        return {transferNumber: 3, value: 'git-3', viewValue: 'git Repositorium', pathDescriptor: 'URL:'}
      case 'DOI':
        return {transferNumber: 4, value: 'doi-4', viewValue: 'DOI Referenz', pathDescriptor: 'DOI:'};
    }
}
}
