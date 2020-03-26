import {Injectable, Optional} from '@angular/core';
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





  getRelationships(project?: Project, resource?: Resource): Observable<Relationship[]> {
    if ((typeof project === 'undefined') && (typeof resource !== 'undefined')) {
      return this.apiService.get('/relationships/?resource=' + resource.id);
    }
    if ((typeof resource === 'undefined') && (typeof project !== 'undefined')) {
      return this.apiService.get('/relationships/?project=' + project.id);
    }
    if ((typeof project !== 'undefined') && (typeof resource !== 'undefined')) {
      return this.apiService.get('/relationships/?resource=' + resource.id + '&project=' + project.id);
    }
    return this.apiService.get('/relationships/');
  }

  createEmptyRelationship(): Observable<Relationship> {
    return this.apiService.post('/relationships/');
  }

  updateRelationship(id: number, relationship: Relationship): Observable<Relationship> {
    return this.apiService.put('/relationships/' + id, relationship);
  }

  createRelationship(project: Project, resource: Resource): Observable<Relationship> {
    return this.createEmptyRelationship().pipe(mergeMap(r => this.updateRelationship(r.id, {id: null, date: null, project, resource})));
  }

  deleteRelationship(relationship: Relationship): Observable<Relationship> {
    return this.apiService.delete('/relationships/' + relationship.id);
  }


}
