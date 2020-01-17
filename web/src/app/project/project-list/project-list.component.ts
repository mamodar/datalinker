import {ApplicationRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Project} from '../../models/project';
import {Observable, of, Subscription} from 'rxjs';
import {StateService} from '../../services/state.service';
import {Resource} from '../../models/resource';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  constructor(private stateService: StateService, private applicationRef: ApplicationRef) { }
  projects$: Observable<Project[]>;
  filterByResource: Subscription;
  selectedProject: Project | undefined;

  ngOnInit() {

    this.filterByResource = this.stateService.getFilterByResource().subscribe(_ => this.refreshProjectsFilterByResources())
    this.projects$ = this.stateService.getProjects();
  }

  refreshProjectsFilterByResources(): void {
    this.projects$ = this.stateService.getProjects();
  }

  onSelect(project: Project) {
    if (this.selectedProject === project) {
      this.selectedProject = undefined;
      this.stateService.setSelectedProject(undefined);
      this.stateService.setFilterByProject(undefined);
    } else {
      this.selectedProject = project;
      this.stateService.setSelectedProject(project);
    }

  }
  filterResourcesBy($event: Project): void {
    this.stateService.setFilterByProject($event);
  }


}
