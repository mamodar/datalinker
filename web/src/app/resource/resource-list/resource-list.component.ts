import {ApplicationRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Project} from '../../models/project';
import {BehaviorSubject, Observable, of, Subscription} from 'rxjs';
import {Resource} from '../../models/resource';
import {StateService} from '../../services/state.service';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-resource-list',
  templateUrl: './resource-list.component.html',
  styleUrls: ['./resource-list.component.css']
})
export class ResourceListComponent implements OnInit {

  constructor(private stateService: StateService) { }
  resources$: Observable<Resource[]>;
  selectedResource$: BehaviorSubject<Resource>;
  displayedColumns: string[] = ['path'];

  ngOnInit() {
    this.resources$ = this.stateService.getResources();
    this.selectedResource$ = this.stateService.getSelectedResource();
  }


  onSelect(resource: Resource): void {

    if (this.selectedResource$.getValue() === resource) {
      this.stateService.setFilterByResource(undefined);
      this.stateService.setSelectedResource(undefined);
    } else {
      this.stateService.setSelectedResource(resource);
    }
  }

  filterByResource($event: Resource): void {
    this.stateService.setFilterByResource($event);
  }
}
