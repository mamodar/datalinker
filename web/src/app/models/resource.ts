export class Resource {
  id: number;
  date: Date;
  location: Location;
  path: string;
  datatype: string;
}

enum Location {
  LOCAL = 'local',
  OPENBIS = 'OpenBIS',
  SAN = 'WissData'
}
