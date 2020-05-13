
export class CloudType {
  value: string;
  viewValue: string;

  constructor(value: string | number) {
    console.log(value);
    switch (value) {
      case 'DROPBOX': case 0:
        return {value: 'DROPBOX', viewValue: 'Dropbox'};
      case 'GOOGLEDRIVE': case 1:
        return {value: 'DROPBOX', viewValue: 'Google Drive'};
      case 'ONEDRIVE': case 2:
        return {value: 'ONEDRIVE', viewValue: 'OneDrive'};
      default:
        return {value: null, viewValue: null};

    }
  }
}
