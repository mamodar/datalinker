import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {debounceTime, map, switchMap} from 'rxjs/operators';
import {Observable, Subject} from 'rxjs';
import {Project} from '../../objects/project';
import {ProjectService} from '../../services/project.service';
import {FormGroup} from '@angular/forms';
import {Relationship} from '../../objects/relationship';

@Component({
  selector: 'app-project-searcher',
  templateUrl: './project-searcher.component.html',
  styleUrls: ['./project-searcher.component.css']
})
export class ProjectSearcherComponent implements OnInit {


  constructor(private projectService: ProjectService) {
  }

  private projectFilter$: Observable<Project[]>;
  private searchText$ = new Subject<string>();
  private selectedProjects: Project[];
  private selectedProject: Project = new Project();
  @Input() formGroup: FormGroup;
  @Output() selectedProjectEvent = new EventEmitter<Project>();
  @Output() selectedProjectRelationships = new EventEmitter<Relationship>();

  ngOnInit() {
    this.projectFilter$ =
      this.searchText$.pipe(
        debounceTime(200),
        switchMap(projectName => this.projectService.getProjects().pipe(
          map(response => response.filter(
            p => this._filter(p.projectName, projectName)))).pipe(
          map(response => this.selectedProjects = response))));
  }

  private search(projectName: string): void {
    this.searchText$.next(projectName);
    if (this.selectedProjects && this.selectedProjects.length === 1) {
      this.selectedProject = this.selectedProjects[0];
    } else {
      this.selectedProject = new Project();
    }
    console.log('event emitted');
    this.selectedProjectEvent.emit(this.selectedProject);
  }

  private _filter(projectName: string, searchTerm: string): boolean {
    return (projectName.trim().toLowerCase().includes(searchTerm.trim().toLowerCase()));
  }


}
