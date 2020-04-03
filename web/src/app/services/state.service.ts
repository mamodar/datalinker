import {ApplicationRef, Injectable} from '@angular/core';
import {BehaviorSubject, iif, merge, Observable, of} from 'rxjs';
import {Relationship} from '../models/relationship';
import {Resource} from '../models/resource';
import {Project} from '../models/project';
import {ProjectService} from './project.service';
import {count, filter, flatMap, map, shareReplay, take, tap} from 'rxjs/operators';
import {RelationshipService} from './relationship.service';
import {ResourceService} from './resource.service';
import {ResourceType} from '../models/resourceType';

@Injectable({
  providedIn: 'root'
})
export class StateService {

  constructor(private projectService: ProjectService,
              private resourceService: ResourceService,
              private relationshipService: RelationshipService) {
  }


  private shownResources = new BehaviorSubject<Resource[]>(undefined);

  private shownProjects = new BehaviorSubject<Project[]>(undefined);
  private selectedProject = new BehaviorSubject<Project>(undefined);
  private filterResourcesByProject = new BehaviorSubject<Project>(undefined);

  private newResources = new Array<Resource>();
  private shownNewResources = new BehaviorSubject<Resource[]>(null);

  private isConnected = new BehaviorSubject<boolean>(false);


  getResources(): BehaviorSubject<Resource[]> {
    console.log('getResources');
    if (this.filterResourcesByProject.getValue()) {
      this.relationshipService.getRelationships(this.filterResourcesByProject.getValue()).
      pipe(map(relationshipArray => relationshipArray.map((relationshipElement) =>
        // @ts-ignore
      {relationshipElement.resource.location = new ResourceType(relationshipElement.resource.location);
       return relationshipElement.resource; }))).
      subscribe(_ => this.shownResources.next(_));
    } else {
      this.shownResources.next([]);
    }

    return this.shownResources;
  }

  getProjects(): BehaviorSubject<Project[]> {
    console.log('getProjects');
    this.projectService.getProjects().subscribe(_ => this.shownProjects.next(_));
    return this.shownProjects;
  }

  getFilterByProject(): BehaviorSubject<Project> {
    console.log(this.filterResourcesByProject.getValue());
    return this.filterResourcesByProject;
  }

  setFilterByProject(selectedProject: Project | undefined): void {
    if (selectedProject !== this.filterResourcesByProject.getValue()) {
      this.filterResourcesByProject.next(selectedProject);
      this.getResources();
    }
  }


  getSelectedProject(): BehaviorSubject<Project> {
    return this.selectedProject;
  }

  setSelectedProject(project: Project): void {
    this.selectedProject.next(project);
  }

  createResource(resource: Resource): Observable<Resource> {
    return this.resourceService.createResource(resource);
  }

  createRelationship(project: Project, resource: Resource): Observable<Relationship> {

    return this.relationshipService.createRelationship(project, resource);
  }

  addNewResource(resource: Resource): void {
    this.newResources.push(resource);
    this.shownNewResources.next(this.newResources);
  }

  getNewShownResources(): BehaviorSubject<Resource[]> {
    return this.shownNewResources;
  }


  resetNewResources(): void {
    this.newResources.length = 0;
    this.shownNewResources.next(this.newResources);
    this.getResources();
  }

  deleteResource(resource: Resource): void {
    console.log(resource);
    if (!resource.id) {
      this.newResources = this.newResources.filter(_ => _ !== resource);
      this.shownNewResources.next(this.newResources);
    } else {
      this.resourceService.deleteResource(resource.id).subscribe(_ => this.getResources());

    }
  }
  /*SAN("WissData"),
  LOCAL("Lokal"),
  OPENBIS("OpenBIS"),
  GIT("git"),
  DOI("doi");*/
  getResourceTypes(): BehaviorSubject<ResourceType[]> {
    const resource: ResourceType[] = [
      {transferNumber: 0, value: 'san-0', viewValue: 'lokal', pathDescriptor: 'Pfad:'},
      {transferNumber: 1, value: 'local-1', viewValue: 'WissData S:', pathDescriptor: 'Pfad:'},
      {transferNumber: 2, value: 'openbis-2', viewValue: 'OpenBIS', pathDescriptor: 'ID:'},
      {transferNumber: 3, value: 'git-3', viewValue: 'git Repositorium', pathDescriptor: 'URL:'},
      {transferNumber: 4, value: 'doi-4', viewValue: 'DOI Referenz', pathDescriptor: 'DOI:'}];
    const resources: BehaviorSubject<ResourceType[]> = new BehaviorSubject<ResourceType[]>(resource);
    return(resources);
  }
}
