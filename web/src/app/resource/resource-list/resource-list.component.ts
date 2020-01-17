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
  filterByProject: Subscription;
  selectedResource: Resource | undefined;

  ngOnInit() {
    // refresh every time a new project is is chosen for filtering
    this.filterByProject = this.stateService.getFilterByProject().subscribe(_ => this.refreshResourcesFilterByProject());
    this.resources$ = this.stateService.getResources();
  }

  refreshResourcesFilterByProject(): void {
    this.resources$ = this.stateService.getResources();
  }

  onSelect(resource: Resource): void {
    if (this.selectedResource === resource) {
      this.selectedResource = undefined;
      this.stateService.setFilterByResource(undefined);
      this.stateService.setSelectedResource(undefined);
    } else {
      this.selectedResource = resource;
      this.stateService.setSelectedResource(resource);
    }
  }

  filterByResource($event: Resource): void {
    this.stateService.setFilterByResource($event);
  }
}
