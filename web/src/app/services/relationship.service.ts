import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../models/project';
import {Resource} from '../models/resource';
import {merge, Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Relationship} from '../models/relationship';
import {ApiService} from './api.service';
import {filter, flatMap, map, mergeMap, pluck, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(private apiService: ApiService) {
  }

  getRelationships(): Observable<Relationship[]> {
    return this.apiService.get( '/relationships/');
  }

  getRelationship(id: number): Observable<Relationship[]> {
    return this.apiService.get('/relationships/' + id);
  }

  getRelationshipsOfProject(project: Project): Observable<Resource[]> {
    return this.apiService.get( '/relationships/projects/' + project.id).pipe(map(rs => rs.map(rse => rse.resource)));
  }

  getRelationshipsOfResource(resource: Resource): Observable<Project[]> {
    return this.apiService.get( '/relationships/resources/' + resource.id).pipe(map(rs => rs.map(rse => rse.project)));
  }

  createEmptyRelationship(): Observable<Relationship> {
    return this.apiService.post('/relationships/');
  }

  updateRelationship(id: number, relationship: Relationship): Observable<Relationship> {
    return this.apiService.put('/relationships/' + id, relationship );
  }

  createRelationship(project: Project, resource: Resource): Observable<Relationship> {
    return this.createEmptyRelationship().pipe(mergeMap(r => this.updateRelationship(r.id, {id: null, date: null, project, resource})));
  }

  deleteRelationship(relationship: Relationship): Observable<void> {
    return this.apiService.delete('/relationships/' + relationship.id);
  }
}
