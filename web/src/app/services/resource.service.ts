import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Resource} from '../models/resource';
import {ApiService} from './api.service';
import {Project} from '../models/project';

/**
 * This service implements the API exposed by the backend for all /resource calls.
 * This service should not by called directly by components but is used by {@link StateService}.
 * @author Kyanoush Yahosseini
 */

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
