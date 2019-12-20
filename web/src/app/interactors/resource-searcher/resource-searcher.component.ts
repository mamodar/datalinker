import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {debounceTime, map, switchMap} from 'rxjs/operators';
import {Resource} from '../../objects/resource';
import {Observable, Subject} from 'rxjs';
import {ResourceService} from '../../services/resource.service';

@Component({
  selector: 'app-resource-searcher',
  templateUrl: './resource-searcher.component.html',
  styleUrls: ['./resource-searcher.component.css']
})
export class ResourceSearcherComponent implements OnInit {

  @Input() formGroup: FormGroup;
  private resourceFilter$: Observable<Resource[]>;
  private searchText$ = new Subject<string>();
  private selectedResources: Resource[];
  private selectedResource: Resource;
  @Output() selectedResourceEvent = new EventEmitter<Resource>();

  constructor(private resourceService: ResourceService) {
  }

  ngOnInit(): void {
    this._getPathFromURL();

    this.resourceFilter$ =
      this.searchText$.pipe(
        debounceTime(200),
        switchMap(path => this.resourceService.getResources().pipe(
          map(response => response.filter(
            p => this._filter(p.path, path)))).pipe(
          map(response => this.selectedResources = response))));
  }


  private _filter(resourceName: string, searchTerm: string): boolean {
    return (resourceName.trim().toLowerCase().includes(searchTerm.trim().toLowerCase()));
  }

  private search(resourcePath: string): void {
    this.searchText$.next(resourcePath);
    if (this.selectedResources && this.selectedResources.length === 1) {
      this.selectedResource = this.selectedResources[0];
    } else {
      this.selectedResource = new Resource();
    }
    console.log('event emitted');
    this.selectedResourceEvent.emit(this.selectedResource);
  }

  private _getPathFromURL() {
    console.log(new URL(window.location.href).searchParams.get('path'));
    if (new URL(window.location.href).searchParams.get('path')) {
      this.formGroup.get('path').setValue(new URL(window.location.href).searchParams.get('path'));

    }
  }
}
