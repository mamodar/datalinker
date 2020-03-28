import {ApplicationRef, Injectable} from '@angular/core';
import {BehaviorSubject, iif, merge, Observable, of} from 'rxjs';
import {Relationship} from '../models/relationship';
import {Resource} from '../models/resource';
import {Project} from '../models/project';
import {ProjectService} from './project.service';
import {count, filter, flatMap, map, shareReplay, take, tap} from 'rxjs/operators';
import {RelationshipService} from './relationship.service';
import {ResourceService} from './resource.service';

@Injectable({
  providedIn: 'root'
})
export class StateService {

  constructor(private projectService: ProjectService,
              private resourceService: ResourceService,
              private relationshipService: RelationshipService) {
  }


  shownResources = new BehaviorSubject<Resource[]>(undefined);
  selectedResource = new BehaviorSubject<Resource>(undefined);
  filterProjectsByResource = new BehaviorSubject<Resource>(undefined);

  shownProjects = new BehaviorSubject<Project[]>(undefined);
  selectedProject = new BehaviorSubject<Project>(undefined);
  filterResourcesByProject = new BehaviorSubject<Project>(undefined);

  isConnected = new BehaviorSubject<boolean>(undefined);

  getResources(): BehaviorSubject<Resource[]> {
    console.log('getResources');
    if (this.filterResourcesByProject.getValue()) {
      this.relationshipService.getRelationships(this.filterResourcesByProject.getValue()).
      pipe(map(relationshipArray => relationshipArray.map(relationshipElement => relationshipElement.resource))).
      subscribe(_ => this.shownResources.next(_));
    } else {
      this.shownResources.next([]);
    }

    return this.shownResources;
  }

  getProjects(): BehaviorSubject<Project[]> {
    console.log('getProjects');
    if (this.filterProjectsByResource.getValue()) {
      this.relationshipService.getRelationships(undefined, this.filterProjectsByResource.getValue()).
      pipe(map(relationshipArray => relationshipArray.map(relationshipElement => relationshipElement.project))).
      subscribe(_ => this.shownProjects.next(_));
    } else {
      this.projectService.getProjects().subscribe(_ => this.shownProjects.next(_));
    }
    return this.shownProjects;
  }

  getFilterByResource(): BehaviorSubject<Resource> {
    return this.filterProjectsByResource;
  }

  setFilterByResource(selectedResource: Resource): void {
    if (selectedResource !== this.filterProjectsByResource.getValue()) {
      this.filterProjectsByResource.next(selectedResource);
      this.setSelectedProject(undefined);
      this.getProjects();
    }
  }

  getFilterByProject(): BehaviorSubject<Project> {
    console.log(this.filterResourcesByProject.getValue());
    return this.filterResourcesByProject;
  }

  setFilterByProject(selectedProject: Project | undefined): void {
    if (selectedProject !== this.filterResourcesByProject.getValue()) {
      this.filterResourcesByProject.next(selectedProject);
      this.setSelectedResource(undefined);
      this.getResources();
    }
  }

  getSelectedResource(): BehaviorSubject<Resource> {
    return this.selectedResource;
  }

  setSelectedResource(resource: Resource): void {
    this.selectedResource.next(resource);
    this.areSelectedConnected();
  }

  getSelectedProject(): BehaviorSubject<Project> {
    return this.selectedProject;
  }

  setSelectedProject(project: Project): void {
    this.selectedProject.next(project);
    this.areSelectedConnected();
  }

  areSelectedConnected(): BehaviorSubject<boolean> {
    console.log('areSelectedConnected');
    if (this.selectedResource && this.selectedProject) {
      this.getRelationshipsForSelected().pipe(tap(_ => console.log(_))).subscribe(_ => this.isConnected.next(_.length > 0));
    } else {
      this.isConnected.next(false);
    }
    return this.isConnected;
  }

getRelationshipsForSelected(): Observable<Relationship[]> {
    return this.relationshipService.getRelationships(this.getSelectedProject().getValue(), this.getSelectedResource().getValue())
    .pipe(map(relationshipArray => relationshipArray));
  }



/*
  changeRelationshipForSelected(): Observable<Relationship> {
    return this.relationshipService.getRelationships(this.getSelectedProject().getValue(), this.getSelectedResource().getValue())
    .pipe(flatMap(relationshipArray => iif(() => relationshipArray.length > 0,
      this.getRelationshipsForSelected().pipe(flatMap(relationship => this.relationshipService.deleteRelationship(relationship))),
      this.relationshipService.createRelationship(this.getSelectedProject().getValue(), this.getSelectedResource().getValue()))));
  }*/
  changeRelationshipSelected(type: string): void {
    if (type === 'connect') {
      this.relationshipService.createRelationship(this.getSelectedProject().getValue(), this.getSelectedResource().getValue()).
      subscribe(() => this.areSelectedConnected());
    }
    if (type === 'disconnect') {
      this.getRelationshipsForSelected().
      pipe(flatMap(relationship => this.relationshipService.deleteRelationship(relationship[0]))).
      subscribe(() => this.areSelectedConnected());
    }
  }
}
