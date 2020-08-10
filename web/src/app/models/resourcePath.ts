import {ResourceType} from './resourceType';

/**
 * This class represents the a resource path.
 * It converts a between a string representation of a path and an object containing value and viewValue.
 * @author Kyanoush Yahosseini
 */
export class ResourcePath {
  value: string;
  viewValue: string;

  public updateFromViewValue(viewValue: string, resourceType: ResourceType): ResourcePath {
    switch (resourceType.value.split('_')[0]) {
      case 'SAN':
        this.viewValue = viewValue;
        this.value = '//rki.local/' + viewValue.split('\\').filter(_ => _.length > 1).filter(_ => _ !== 'S:').join('/');
        return this;
        break;
      default:
        this.viewValue = viewValue;
        this.value = viewValue;
        return this;
    }
  }

  public updateFromValue(value: string, resourceType: ResourceType): ResourcePath {
    switch (resourceType.value.split('_')[0]) {
      case 'SAN':
        this.value = value;
        this.viewValue = 'S:\\' + value.split('/').filter(_ => _.length > 1).filter(_ => _ !== 'rki.local').join('\\');
        return this;
        break;
      default:
        this.value = value;
        this.viewValue = value;
        return this;
    }
  }
}
