import {Component, Input, OnInit} from '@angular/core';
import {RelationshipService} from '../services/relationship.service';
import {Relationship} from '../models/relationship';
import {BehaviorSubject, Observable, Subscription} from 'rxjs';
import {StateService} from '../services/state.service';
import {map, tap} from 'rxjs/operators';
import {Project} from '../models/project';
import {Resource} from '../models/resource';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.css']
})
export class RelationshipComponent implements OnInit {
  private selectedProject$: Observable<Project>;
  private selectedResource$: Observable<Resource>;


  constructor(private stateService: StateService) {
  }

  ngOnInit() {
    this.selectedProject$ = this.stateService.getSelectedProject().pipe();
    this.selectedResource$ = this.stateService.getSelectedResource().pipe();

  }

}
