import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../models/project';
import {BehaviorSubject, Observable} from 'rxjs';
import {StateService} from '../services/state.service';
import {Resource} from '../models/resource';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  private shownResources$: Observable<Resource[]>;

  constructor(private stateService: StateService) { }
  @Input() projects$: Observable<Project[]>;
  selectedProject$: BehaviorSubject<Project>;
  projectData: string;

  ngOnInit() {
    this.selectedProject$ = this.stateService.getSelectedProject();
    this.shownResources$ = this.stateService.getResources();
  }

  onSelect(project: Project): void {

    if (this.selectedProject$.getValue() === project) {
      this.stateService.setSelectedProject(undefined);
      this.shownResources$ = this.stateService.setFilterByProject(undefined);
    } else {
      this.stateService.setSelectedProject(project);
      this.shownResources$ = this.stateService.setFilterByProject(project);
    }

  }
}
