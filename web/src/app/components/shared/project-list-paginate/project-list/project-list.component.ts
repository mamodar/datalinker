import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../../../../models/project';
import {BehaviorSubject, Observable} from 'rxjs';
import {StateService} from '../../../../services/state.service';
import {Resource} from '../../../../models/resource';
import {Value} from '../../../../models/value';

/**
 * This component shows projects as an expansion panel.
 *
 * @author Kyanoush Yahosseini
 */

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  public shownValues$: Observable<Value[]>;
  constructor(public stateService: StateService) {
  }
  @Input() projects$: Observable<Project[]>;
  selectedProject$: BehaviorSubject<Project>;
  public shownResources$: Observable<Resource[]>;

  ngOnInit() {
    this.selectedProject$ = this.stateService.getSelectedProject();
    this.shownResources$ = this.stateService.getResources();
    this.shownValues$ = this.stateService.getValuesForSelectedProject();
  }

  onSelect(project: Project): void {

    if (this.selectedProject$.getValue() === project) {
      this.stateService.setSelectedProject(undefined);
      this.shownResources$ = this.stateService.setFilterByProject(undefined);
      this.shownValues$ = this.stateService.getValuesForSelectedProject();
    } else {
      this.stateService.setSelectedProject(project);
      this.shownResources$ = this.stateService.setFilterByProject(project);
      this.shownValues$ = this.stateService.getValuesForSelectedProject();
    }

  }
}
