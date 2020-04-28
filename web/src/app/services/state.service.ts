import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Relationship} from '../models/relationship';
import {Resource} from '../models/resource';
import {Project} from '../models/project';
import {ProjectService} from './project.service';
import {debounceTime, distinctUntilChanged, flatMap, map} from 'rxjs/operators';
import {RelationshipService} from './relationship.service';
import {ResourceService} from './resource.service';
import {ResourceType} from '../models/resourceType';
import {AuthUser} from '../models/authUser';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})
export class StateService {


  constructor(private projectService: ProjectService,
              private resourceService: ResourceService,
              private relationshipService: RelationshipService,
              private apiService: ApiService) {
  }


  private shownResources = new BehaviorSubject<Resource[]>(undefined);
  private shownProjects = new BehaviorSubject<Project[]>(undefined);
  private selectedProject = new BehaviorSubject<Project>(undefined);
  private filterResourcesByProject = new BehaviorSubject<Project>(undefined);
  private loggedIn = new BehaviorSubject<AuthUser>(undefined);

  private newResources = new Array<Resource>();
  private shownNewResources = new BehaviorSubject<Resource[]>(null);


  getResources(): BehaviorSubject<Resource[]> {
    if (this.filterResourcesByProject.getValue()) {
      this.relationshipService.getResourcesForProject(this.filterResourcesByProject.getValue()).subscribe(_ => this.shownResources.next(_));
    } else {
      this.shownResources.next([]);
    }

    return this.shownResources;
  }

  getProjects(): BehaviorSubject<Project[]> {
    this.projectService.getProjects().subscribe(_ => this.shownProjects.next(_));
    return this.shownProjects;
  }

  searchProjects(terms: Observable<string>): Observable<Project[]> {
    return terms.pipe(debounceTime(400), distinctUntilChanged(),
      flatMap(term => this.projectService.searchProjects(term)));
  }

  getFilterByProject(): BehaviorSubject<Project> {
    return this.filterResourcesByProject;
  }

  setFilterByProject(selectedProject: Project | undefined): Observable<Resource[]> {
    if (selectedProject !== this.filterResourcesByProject.getValue()) {
      this.filterResourcesByProject.next(selectedProject);

    }
    return this.getResources();
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
      new ResourceType(0), new ResourceType(1), new ResourceType(2), new ResourceType(3), new ResourceType(4)
    ];
    const resources: BehaviorSubject<ResourceType[]> = new BehaviorSubject<ResourceType[]>(resource);
    return (resources);
  }

  updateResource(resource: Resource): Observable<Resource> {
    return this.resourceService.updateResource(resource.id, resource);

  }

  getLoggedInUser(): BehaviorSubject<AuthUser> {
    return this.loggedIn;
  }

  loginUser(user: string, password: string): Observable<any> {
    console.log('user: ' + user + ' password: ' + password);
    return this.apiService.get('/auth', {userName: user, password}).pipe(map(response => {
        if (response.name) {
          this.loggedIn.next({userName: user, password});
        } else {
          this.loggedIn.next(undefined);
        }
      }
      )
    );
  }
}
