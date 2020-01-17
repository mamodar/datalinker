import { Component, OnInit } from '@angular/core';
import {RelationshipService} from '../services/relationship.service';
import {Relationship} from '../models/relationship';
import {Observable, Subscription} from 'rxjs';
import {StateService} from '../services/state.service';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.css']
})
export class RelationshipComponent implements OnInit {
  private selectedProject: Subscription;
  private selectedResource: Subscription;
  showButton: boolean;

  constructor(private stateService: StateService) { }

  ngOnInit() {
    this.selectedProject = this.stateService.getSelectedProject().subscribe(_ => this.checkForConnection());
    this.selectedResource = this.stateService.getSelectedResource().subscribe(_ => this.checkForConnection());
  }

  private checkForConnection() {
    if (this.stateService.getSelectedProject().getValue() && this.stateService.getSelectedResource().getValue()) {
      this.showButton = true;
      if (this.stateService.checkIfConnected(this.stateService.getSelectedProject().getValue(), this.stateService.getSelectedResource().getValue())) {

      } else {

      }
    } else {
      this.showButton = false;
    }
  }
}
