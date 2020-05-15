
export class CloudType {
  value: string;
  viewValue: string;

  constructor(value: string | number) {
    console.log(value);
    switch (value) {
      case 'DROPBOX':
      case 'Dropbox':
      case 0:
        return {value: 'DROPBOX', viewValue: 'Dropbox'};
      case 'GOOGLEDRIVE':
      case 'Google Drive':
      case 1:
        return {value: 'GOOGLEDRIVE', viewValue: 'Google Drive'};
      case 'ONEDRIVE':
      case 'OneDrive':
      case 2:
        return {value: 'ONEDRIVE', viewValue: 'OneDrive'};
      default:
        return {value: null, viewValue: null};

    }
  }
}
