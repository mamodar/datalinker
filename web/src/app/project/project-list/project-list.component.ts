import {Component, OnInit} from '@angular/core';
import {Project} from '../../models/project';
import {BehaviorSubject, Observable} from 'rxjs';
import {StateService} from '../../services/state.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  constructor(private stateService: StateService) { }
  projects$: Observable<Project[]>;

  selectedProject$: BehaviorSubject<Project>
  displayedColumns: string[] = ['name'];
  // selectionModel =  new SelectionModel<Project>(false,[]);
  ngOnInit() {
    this.projects$ = this.stateService.getProjects();
    this.selectedProject$ = this.stateService.getSelectedProject();

  }

  onSelect(project: Project) {
    if (this.selectedProject$.getValue() === project) {
      this.stateService.setSelectedProject(undefined);
      this.stateService.setFilterByProject(undefined);
    } else {
      this.stateService.setSelectedProject(project);
    }

  }
  filterResourcesBy($event: Project): void {
    this.stateService.setFilterByProject($event);
  }


}
