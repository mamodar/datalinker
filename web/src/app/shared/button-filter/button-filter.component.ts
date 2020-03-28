import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {StateService} from '../../services/state.service';
import {Project} from '../../models/project';
import {BehaviorSubject} from 'rxjs';

import {Resource} from '../../models/resource';


@Component({
  selector: 'app-button-filter',
  templateUrl: './button-filter.component.html',
  styleUrls: ['./button-filter.component.css']
})
export class ButtonFilterComponent implements OnInit {
  checked: boolean;
  @Input() parent: Project|Resource;
  constructor(private stateService: StateService) { }

  ngOnInit() {
  }

  filter($event) {
    if(!(this.parent instanceof Project) || this.parent.projectName){
    if ($event.target.checked) {
      this.stateService.setFilterByProject(this.stateService.getSelectedProject().getValue());
    } else {
      this.stateService.setFilterByProject(undefined);
    }
  }
    if(!(this.parent instanceof Resource) || this.parent.path ){
      if ($event.target.checked) {
        this.stateService.setFilterByResource(this.stateService.getSelectedResource().getValue());
      } else {
        this.stateService.setFilterByResource(undefined);
      }
    }
  }
}
