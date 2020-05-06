import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Resource} from '../models/resource';
import {Project} from '../models/project';
import {ProjectService} from './project.service';
import {debounceTime, distinctUntilChanged, flatMap, map, tap} from 'rxjs/operators';
import {ResourceService} from './resource.service';
import {ResourceType} from '../models/resourceType';
import {AuthUser} from '../models/authUser';
import {ApiService} from './api.service';
import {User} from '../models/user';



@Injectable({
  providedIn: 'root'
})
export class StateService {


  constructor(private projectService: ProjectService,
              private resourceService: ResourceService,
              private apiService: ApiService) {
    this.currentUser.next({
      userName: sessionStorage.getItem('currentUser'),
      password: sessionStorage.getItem('currentPassword')
    });
  }


  private shownResources = new BehaviorSubject<Resource[]>(undefined);
  private shownProjects = new BehaviorSubject<Project[]>(undefined);
  private selectedProject = new BehaviorSubject<Project>(undefined);
  private filterResourcesByProject = new BehaviorSubject<Project>(undefined);
  private currentUser = new BehaviorSubject<AuthUser>(undefined);
  private newResources = new Array<Resource>();
  private shownNewResources = new BehaviorSubject<Resource[]>(null);


  getResources(): BehaviorSubject<Resource[]> {
    if (this.filterResourcesByProject.getValue()) {
      this.projectService.getProject(
        this.filterResourcesByProject.getValue())
      .subscribe(_ => {
        // @ts-ignore comes in as a string
        _.resources.map( resource => resource.location = new ResourceType(resource.location));
        this.shownResources.next(_.resources);
      });
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

  createResource(resource: Resource, project?: Project, ): Observable<Resource> {
    if (project) {
    return this.resourceService.createResource(resource, project);
    }
    return this.resourceService.createResource(resource, this.getSelectedProject().getValue());
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
    return this.resourceService.updateResource(resource);
  }

  getLoggedInUser(): BehaviorSubject<AuthUser> {
    return this.currentUser;
  }

  loginUser(user: string, password: string): Observable<any> {
    this.currentUser.next(undefined);
    sessionStorage.clear();
    return this.apiService.get('/user', {userName: user, password}).pipe(map(response => {
        if (response.name) {
          this.currentUser.next({userName: user, password});
          sessionStorage.setItem('currentUser', user);
          sessionStorage.setItem('currentPassword', password);

        } else {
          console.warn('loginUser: ' + user + ' failed');
        }
    }));
  }

}
