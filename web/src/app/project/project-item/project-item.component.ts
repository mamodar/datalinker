import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Project} from '../../models/project';
import {BehaviorSubject} from 'rxjs';
import {StateService} from '../../services/state.service';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {

  constructor(private stateService: StateService) { }

   @Input() project: Project;
   selected$: BehaviorSubject<Project>;

  ngOnInit() {
    this.selected$ = this.stateService.getSelectedProject() ;
  }

  onChecked($event: boolean) {
    if ($event) {
      this.stateService.setFilterByProject(this.project);
    } else {
      this.stateService.setFilterByProject(undefined);
    }
  }
}
