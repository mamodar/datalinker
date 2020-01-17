import {ApplicationRef, Injectable} from '@angular/core';
import {BehaviorSubject, merge, Observable} from 'rxjs';
import {Relationship} from '../models/relationship';
import {Resource} from '../models/resource';
import {Project} from '../models/project';
import {ProjectService} from './project.service';
import {count, filter, map, shareReplay, tap} from 'rxjs/operators';
import {RelationshipService} from './relationship.service';
import {ResourceService} from './resource.service';

@Injectable({
  providedIn: 'root'
})
export class StateService {
  private connected: number;


  constructor(private projectService: ProjectService,
              private resourceService: ResourceService,
              private relationshipService: RelationshipService) {
  }

  filterByResource = new BehaviorSubject<Resource>(undefined);
  selectedResource = new BehaviorSubject<Resource>(undefined);
  filterByProject = new BehaviorSubject<Project>(undefined);
  selectedProject = new BehaviorSubject<Project>(undefined);

  getResources(): Observable<Resource[]> {
    console.log('getResources');
    if (this.filterByProject.getValue()) {
      return this.relationshipService.getRelationshipsOfProject(this.filterByProject.getValue());
    }
    return this.resourceService.getResources();
  }

  getProjects(): Observable<Project[]> {
    console.log('getProjects');
    if (this.filterByResource.getValue()) {
        return this.relationshipService.getRelationshipsOfResource(this.filterByResource.getValue());
    }
    return this.projectService.getProjects();
  }

  getFilterByResource(): BehaviorSubject<Resource> {
    return this.filterByResource;
  }

  setFilterByResource(selectedResource: Resource): void {
    if (selectedResource !== this.filterByResource.getValue()) {
    this.filterByResource.next(selectedResource);
    }
  }

  getFilterByProject(): BehaviorSubject<Project> {
    console.log(this.filterByProject.getValue());
    return this.filterByProject;
  }

  setFilterByProject(selectedProject: Project| undefined): void {
    if (selectedProject !== this.filterByProject.getValue()) {
      this.filterByProject.next(selectedProject);
    }
  }

  setSelectedResource(resource: Resource) {
    this.selectedResource.next(resource);
  }
  setSelectedProject(project: Project) {
    this.selectedProject.next(project);
  }
  getSelectedResource(): BehaviorSubject<Resource> {
    return this.selectedResource;
  }
  getSelectedProject(): BehaviorSubject<Project> {
    return this.selectedProject;
  }

  checkIfConnected(project: Project, resource: Resource): number {
    console.log('checkIfConnected: ' + project.projectName);
    console.log('checkIfConnected: ' + resource.path);
    // TODO(ky): better check for equality
    // TODO(ky): check why always one to late
    // this.relationshipService.getRelationshipsOfProject(project).pipe(map(rsa => rsa.filter(rsi  => rsi.path === resource.path), ), map(rs => this.connected = rs.length)).subscribe();
    // console.log('checkIfConnected ' + this.connected);
    // return this.connected;
    return undefined;
  }
}
