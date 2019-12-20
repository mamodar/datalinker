import {Injectable} from '@angular/core';
import {Project} from '../objects/project';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class ProjectService {

  constructor(private http: HttpClient) {
  }

  getProjects(): Observable<Project[]> {
    console.warn(Date() + ': ' + environment.appUrl + '/projects/');
    return this.http.get<Project[]>(environment.appUrl + '/projects/');
  }

  getProject(id: number): Observable<Project> {
    return this.http.get<Project>(environment.appUrl + '/projects/' + id);
  }
}
