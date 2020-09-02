import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {ResourceLocation} from '../models/resourceLocation';

@Injectable({
  providedIn: 'root'
})
export class TypeService {
  private resourceLocationTypes: ResourceLocation[];
  private licenseTypes: string[];
  private resourceTypeTypes: string[];

  constructor() {
    this.initializeTypes();
  }

  public getResourceLocations(): BehaviorSubject<ResourceLocation[]> {
    const resourceLocationTypes: BehaviorSubject<ResourceLocation[]> = new BehaviorSubject<ResourceLocation[]>(this.resourceLocationTypes);
    return (resourceLocationTypes);
  }

  public getLicenseTypes(): BehaviorSubject<string[]> {
    const licenseTypes: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(this.licenseTypes);
    return (licenseTypes);
  }

  public getResourceTypeTypes(): BehaviorSubject<string[]> {
    const resourceTypeTypes: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(this.resourceTypeTypes);
    return (resourceTypeTypes);
  }

  private initializeTypes(): void {
    this.resourceLocationTypes = [];
    this.resourceLocationTypes.push(new ResourceLocation('SAN_OU'));
    this.resourceLocationTypes.push(new ResourceLocation('SAN_PROJECT'));
    this.resourceLocationTypes.push(new ResourceLocation('SAN_DATA'));
    this.resourceLocationTypes.push(new ResourceLocation('OPENBIS'));
    this.resourceLocationTypes.push(new ResourceLocation('GIT'));
    this.resourceLocationTypes.push(new ResourceLocation('URL'));
    this.resourceLocationTypes.push(new ResourceLocation('DOI'));
    this.resourceLocationTypes.push(new ResourceLocation('DMS'));

    this.licenseTypes = [];
    this.licenseTypes.push('Namensnennung 3.0 (CC BY 3.0)');
    this.licenseTypes.push('Keine Bearbeitung  3.0 (CC BY ND 3.0)');
    this.licenseTypes.push('Nicht kommerziell 3.0 (CC BY NC 3.0)');
    this.licenseTypes.push('Nicht-kommerziell - Weitergabe unter gleichen Bedingungen 3.0 (CC BY-NC-SA)');
    this.licenseTypes.push('Nicht offen/Keine');

    this.resourceTypeTypes = [];
    this.resourceTypeTypes.push('Publikation');
    this.resourceTypeTypes.push('Poster');
    this.resourceTypeTypes.push('Präsentation');
    this.resourceTypeTypes.push('Datensatz');
    this.resourceTypeTypes.push('Bild');
    this.resourceTypeTypes.push('Video/Audio');
    this.resourceTypeTypes.push('Lektion');

  }
}
