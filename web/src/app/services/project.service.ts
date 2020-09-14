import {Injectable} from '@angular/core';
import {Project} from '../models/project';
import {Observable} from 'rxjs';
import {ApiService} from './api.service';
import {Resource} from '../models/resource';
import {Value} from '../models/value';

/**
 * This service implements the API exposed by the backend for all /projects calls.
 * This service should not by called directly by components but is used by {@link StateService}.
 * @author Kyanoush Yahosseini
 */
@Injectable({
  providedIn: 'root'
})

export class ProjectService {

  constructor(  private apiService: ApiService) {
  }

  getProjects(): Observable<Project[]> {

    return this.apiService.get('/projects');
  }

  getResourcesOfProject(project: Project): Observable<Resource[]> {
    return this.apiService.get('/projects/' + project.id + '/resources');
  }

  searchProjects(search?: string, filter?: string): Observable<Project[]> {

    if (search && filter) {
      return this.apiService.get('/projects/search?search=' + search + '&filter=' + filter);
    }
    if (search) {
      return this.apiService.get('/projects/search?search=' + search);
    }
    if (filter) {
      return this.apiService.get('/projects/search?filter=' + filter);
    }
    return this.apiService.get('/projects/search');

  }
  getValuesOfProject(project: Project): Observable<Value[]> {
    return this.apiService.get('/projects/' + project.id + '/values');
  }


}
