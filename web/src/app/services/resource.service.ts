import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Resource} from '../objects/resource';
import {tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class ResourceService {

  constructor(private http: HttpClient) {
  }

  getResources(): Observable<Resource[]> {
    return this.http.get<Resource[]>(environment.appUrl + '/resources/');
  }

  getResource(id: number): Observable<Resource[]> {
    return this.http.get<Resource[]>(environment.appUrl + '/resources/' + id);
  }

  addResource(resource: Resource): Observable<Resource> {
    return this.http.post<Resource>(environment.appUrl + '/resources/', resource).pipe(tap((newResource: Resource) =>
      console.log(`added resource id = ${newResource.id}`)));
  }

  updateResource(resource: Resource): Observable<Resource> {
    return this.http.put<Resource>(environment.appUrl + '/resources/', resource).pipe(tap((newResource: Resource) =>
      console.log(`updated resource id = ${newResource.id}`)));
  }

}
