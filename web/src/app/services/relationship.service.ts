import {Injectable, Optional} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../models/project';
import {Resource} from '../models/resource';
import {merge, Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Relationship} from '../models/relationship';
import {ApiService} from './api.service';
import {filter, flatMap, map, mergeMap, pluck, tap} from 'rxjs/operators';
import {ResourceType} from '../models/resourceType';


@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(private apiService: ApiService) {
  }

  getResourcesForProject(project: Project): Observable<Resource[]> {
    return this.apiService.get('/relationships/?project=' + project.id).pipe(
      map(relationshipArray => relationshipArray.
      // convert location: string to location: ResourceType
      map((relationshipElement) => {
        // @ts-ignore resource.location is a string when reading from REST API
        relationshipElement.resource.location = new ResourceType(relationshipElement.resource.location);
        return relationshipElement.resource;
      })));
  }

  private createEmptyRelationship(): Observable<Relationship> {
    return this.apiService.post('/relationships/');
  }

  updateRelationship(id: number, relationship: Relationship): Observable<Relationship> {
    return this.apiService.put('/relationships/' + id, relationship);
  }

  createRelationship(project: Project, resource: Resource): Observable<Relationship> {
    return this.createEmptyRelationship().pipe(
      mergeMap(r => this.updateRelationship(r.id, {id: null, date: null, project, resource})));
  }

  deleteRelationship(relationship: Relationship): Observable<Relationship> {
    return this.apiService.delete('/relationships/' + relationship.id);
  }


}
