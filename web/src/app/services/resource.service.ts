import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ObjectUnsubscribedError, Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Resource} from '../models/resource';
import {map, mergeMap, tap} from 'rxjs/operators';
import {ApiService} from './api.service';
import {Project} from '../models/project';

@Injectable({
  providedIn: 'root'
})

export class ResourceService {

  constructor(private apiService: ApiService) {
  }

  getResources(): Observable<Resource[]> {

    return this.apiService.get('/resources');
  }

  getResource(id: number): Observable<Resource[]> {
    return this.apiService.get('/resources/' + id);
  }

  updateResource( resource: Resource): Observable<Resource> {
    // @ts-ignore goes out  as a string
    resource.location = resource.location.value;
    // @ts-ignore goes out  as a string
    resource.path = resource.path.value;
    return this.apiService.put('/resources/' + resource.id, resource);
  }

  createResource(resource: Resource, project: Project): Observable<Resource> {
    resource.projectId = project.id;
    // @ts-ignore goes out  as a string
    resource.location = resource.location.value;
    // @ts-ignore goes out  as a string
    resource.path = resource.path.value;
    return this.apiService.post('/resources/', resource );
  }

  deleteResource(id: number): Observable<void> {
    return this.apiService.delete('/resources/' + id);
  }

}
