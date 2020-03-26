import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Resource} from '../../models/resource';
import {Project} from '../../models/project';
import {BehaviorSubject, iif} from 'rxjs';
import {StateService} from '../../services/state.service';
import {map, tap} from 'rxjs/operators';

@Component({
  selector: 'app-resource-item',
  templateUrl: './resource-item.component.html',
  styleUrls: ['./resource-item.component.css']
})
export class ResourceItemComponent implements OnInit {

  constructor(private stateService: StateService) { }
  @Input() resource: Resource;
  selected$: BehaviorSubject<Resource>;

  ngOnInit() {
    this.selected$ = this.stateService.getSelectedResource();
  }
  onChecked($event: boolean): void {
    if ($event) {
      this.stateService.setFilterByResource(this.resource);
    } else {
      this.stateService.setFilterByResource(undefined);
    }
  }
}
