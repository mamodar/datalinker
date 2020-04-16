import {Injectable} from '@angular/core';
import {Project} from '../models/project';
import {Observable} from 'rxjs';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})

export class ProjectService {

  constructor(  private apiService: ApiService) {
  }

  getProjects(): Observable<Project[]> {

    return this.apiService.get('/projects/rdmo');
  }

  getProject(id: number): Observable<Project> {
    return this.apiService.get('/projects/' + id);
  }

  searchProjects(query: string): Observable<Project[]> {
    return this.apiService.get('/projects?search='+ query);
  }
}
