import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ObjectUnsubscribedError, Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Resource} from '../models/resource';
import {mergeMap, tap} from 'rxjs/operators';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})

export class ResourceService {

  constructor(private apiService: ApiService) {
  }

  getResources(): Observable<Resource[]> {
    return this.apiService.get('/resources/');
  }

  getResource(id: number): Observable<Resource[]> {
    return this.apiService.get('/resources/' + id);
  }

  createEmptyResource(): Observable<Resource> {
    return this.apiService.post( '/resources/');
  }

  updateResource(id: number, resource: Resource): Observable<Resource> {
    return this.apiService.put( '/resources/' + id, resource);
  }
  createResource(resource: Resource): Observable<Resource> {
    return this.createEmptyResource().pipe(mergeMap(r => this.updateResource(r.id, resource)));
  }
  deleteResource(id: number): Observable<void> {
    return this.apiService.delete('resources' + id);
  }

}
