import {Injectable} from '@angular/core';
import {BehaviorSubject, combineLatest, Observable, of} from 'rxjs';
import {Resource} from '../models/resource';
import {Project} from '../models/project';
import {ProjectService} from './project.service';
import {concatMap, debounceTime, distinctUntilChanged, flatMap, map} from 'rxjs/operators';
import {ResourceService} from './resource.service';
import {ResourceType} from '../models/resourceType';
import {AuthUser} from '../models/authUser';
import {ApiService} from './api.service';
import {ResourcePath} from '../models/resourcePath';
import {Value} from '../models/value';
import {PublicationService} from './publication.service';
import {PublicationDTO} from '../models/publicationDTO';
import {PublishedDTO} from '../models/publishedDTO';


/**
 * This service provides the current state of the application to all other components.
 * It contains the single source of truth while allowing communication between unrelated components.
 * It also hides all calls to the backend from other components.
 * @todo KY should cleaned
 * @author Kyanoush Yahosseini
 */
@Injectable({
  providedIn: 'root'
})
export class StateService {



  constructor(private projectService: ProjectService,
              private resourceService: ResourceService,
              private publicationService: PublicationService,
              private apiService: ApiService) {
    this.currentUser.next({
      userName: sessionStorage.getItem('currentUser'),
      password: sessionStorage.getItem('currentPassword')
    });
    this.initializeTypes();
  }


  private shownResources = new BehaviorSubject<Resource[]>(undefined);
  private shownProjects = new BehaviorSubject<Project[]>(undefined);
  private shownValues = new BehaviorSubject<Value[]>(undefined);
  private selectedProject = new BehaviorSubject<Project>(undefined);
  private filterResourcesByProject = new BehaviorSubject<Project>(undefined);
  private currentUser = new BehaviorSubject<AuthUser>(null);
  private newResources = new Array<Resource>();
  private shownNewResources = new BehaviorSubject<Resource[]>([]);
  private resourceTypes: ResourceType[];

  public getResources(): BehaviorSubject<Resource[]> {
    if (this.filterResourcesByProject.getValue()) {
      this.projectService.getResourcesOfProject(this.filterResourcesByProject.getValue())
      .subscribe(resources => {
        // @ts-ignore comes in as a string
        resources.map(resource => resource.location = new ResourceType(resource.location));
        // @ts-ignore comes in as a string
        resources.map(resource => resource.path = new ResourcePath().updateFromValue(resource.path, resource.location));
        this.shownResources.next(resources);
      });
    } else {
      this.shownResources.next([]);
    }

    return this.shownResources;
  }

  public getValuesForSelectedProject(): BehaviorSubject<Value[]> {
    if (this.filterResourcesByProject.getValue()) {
      this.projectService.getValuesOfProject(this.filterResourcesByProject.getValue())
      .subscribe(values => this.shownValues.next(this.reduceValueCollections(values.map(value => new Value(value)))));
    } else {
      this.shownResources.next([]);
    }
    return this.shownValues;
  }

  public getProjects(): BehaviorSubject<Project[]> {
    this.projectService.getProjects().subscribe(_ => this.shownProjects.next(_));
    return this.shownProjects;
  }


  public searchProjects(searchTerm: Observable<string>, filterTerm: Observable<string>): Observable<Project[]> {

    return combineLatest(searchTerm, filterTerm).pipe(debounceTime(10000), distinctUntilChanged(),
      flatMap(value => this.projectService.searchProjects(value[0], value[1])));
  }

  public setFilterByProject(selectedProject: Project | undefined): Observable<Resource[]> {
    if (selectedProject !== this.filterResourcesByProject.getValue()) {
      this.filterResourcesByProject.next(selectedProject);
    }
    return this.getResources();
  }


  public getSelectedProject(): BehaviorSubject<Project> {
    return this.selectedProject;
  }

  public setSelectedProject(project: Project): void {
    this.selectedProject.next(project);
  }

  public createResource(resource: Resource, project?: Project, ): Observable<Resource> {
    if (project) {
      return this.resourceService.createResource(resource, project);
    }
    return this.resourceService.createResource(resource, this.getSelectedProject().getValue());
  }

  public addNewResource(resource: Resource): void {
    this.newResources.push(resource);
    this.shownNewResources.next(this.newResources);
  }

  public getNewShownResources(): BehaviorSubject<Resource[]> {
    return this.shownNewResources;
  }


  public resetNewResources(): void {
    this.newResources.length = 0;
    this.shownNewResources.next(this.newResources);
    this.getResources();
  }

  public deleteResource(resource: Resource): void {
    if (!resource.id) {
      this.newResources = this.newResources.filter(_ => _ !== resource);
      this.shownNewResources.next(this.newResources);
    } else {
      this.resourceService.deleteResource(resource.id).subscribe(_ => this.getResources());

    }
  }

  public getResourceTypes(): BehaviorSubject<ResourceType[]> {
    const resourceTypes: BehaviorSubject<ResourceType[]> = new BehaviorSubject<ResourceType[]>(this.resourceTypes);
    return (resourceTypes);
  }

  public updateResource(resource: Resource): Observable<Resource> {
    return this.resourceService.updateResource(resource);
  }

  public getValues(): Observable<Value[]> {
    return this.apiService.get('/values').pipe(
      map((values: Value[]) => values.map((value: Value) => new Value(value))));
  }

  public getLoggedInUser(): BehaviorSubject<AuthUser | null> {
    return this.currentUser;
  }

  public loginUser(user: string, password: string): Observable<any> {
    console.log('loginUser: ' + user);
    this.currentUser.next(null);
    sessionStorage.clear();
    return this.apiService.get('/user', {userName: user, password}).pipe(map(response => {
      if (response.name) {
        this.currentUser.next({userName: user, password});
        sessionStorage.setItem('currentUser', user);
        sessionStorage.setItem('currentPassword', password);
      }
    }));
  }

  public logoutUser(): Observable<any> {
    console.log('logoutUser');
    this.currentUser.next(null);
    sessionStorage.clear();
    return (of(true));
  }


  public publishToExternalService(type: string, transferObject: PublicationDTO): Observable<any> {
    switch (type) {
      case 'edoc':
        console.log('publishToExternalService' + transferObject.title, transferObject.files.forEach(value => value.name));
        return this.publicationService.createItemDspace(transferObject).pipe(
          concatMap((value: PublishedDTO) => this.publicationService.uploadBitstreamDspace(value.id, transferObject)));
      case 'zenodo':
        console.log('publishToExternalService' + transferObject.title, transferObject.files.forEach(value => value.name));
        return this.publicationService.createItemZenodo(transferObject).pipe(
          concatMap((value: PublishedDTO) => this.publicationService.uploadBitstreamZenodo(value.id, transferObject)));
    }
  }

  private initializeTypes(): void {
    this.resourceTypes = [];
    this.resourceTypes.push(new ResourceType('SAN_OU'));
    this.resourceTypes.push(new ResourceType('SAN_PROJECT'));
    this.resourceTypes.push(new ResourceType('SAN_DATA'));
    this.resourceTypes.push(new ResourceType('OPENBIS'));
    this.resourceTypes.push(new ResourceType('GIT'));
    this.resourceTypes.push(new ResourceType('DOI'));
    this.resourceTypes.push(new ResourceType('DMS'));
  }

  private reduceValueCollections(values: Value[]): Value[] {
    const reducedValues: Value[] = [];
    values.forEach(
      value => {
        if (reducedValues.find(reducedValue => value.questionText === reducedValue.questionText)) {
          reducedValues.find(reducedValue => value.questionText === reducedValue.questionText).appendAnswerText(', ' + value.answerText);
        } else {
          reducedValues.push(value);
        }
      });
    return reducedValues;
  }

}
