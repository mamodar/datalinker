import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../objects/project';
import {Resource} from '../objects/resource';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Relationship} from '../objects/relationship';

@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(private http: HttpClient) {
  }

  getRelationships(): Observable<Relationship[]> {
    return this.http.get<Relationship[]>(environment.appUrl + '/relationships/');
  }

  getRelationshipsOfProject(project: Project): Observable<Relationship[]> {
    return this.http.get<Relationship[]>(environment.appUrl + '/relationships/projects/' + project.id);
  }

  getRelationshipsOfResource(resource: Resource): Observable<Relationship[]> {
    return this.http.get<Relationship[]>(environment.appUrl + '/relationships/resources/' + resource.id);
  }

  addRelationship(project: Project, resource: Resource): Observable<Relationship[]> {

    const relationship: Relationship = {id: null, date: null, project, resource};
    console.log('PUT' + JSON.stringify(relationship));
    return this.http.put<Relationship[]>(environment.appUrl + '/relationships/', relationship);
  }

  deleteRelationship(relationship: Relationship): Observable<void> {
    return this.http.delete<void>(environment.appUrl + '/relationships/' + relationship.id);
  }
}
