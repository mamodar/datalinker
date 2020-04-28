import { Component, OnInit } from '@angular/core';
import {StateService} from '../services/state.service';
import {flatMap, take, tap} from 'rxjs/operators';
import {Resource} from '../models/resource';

@Component({
  selector: 'app-new-resource-attach',
  templateUrl: './new-resource-attach.component.html',
  styleUrls: ['./new-resource-attach-button.component.css']
})
export class NewResourceAttachButtonComponent implements OnInit {

  constructor(public stateService: StateService) { }

  ngOnInit(): void {
  }

  connect() {
    console.log('connect');
    this.stateService.getNewShownResources().
    pipe(flatMap(_ => _), tap(_ => console.log('connect: ' + _.path))).
    subscribe(
      resource => this.stateService.createResource(resource).
      pipe(take(1)).
      subscribe(
        newResource =>
          this.stateService.createRelationship(this.stateService.getSelectedProject().getValue(), newResource).
          pipe(take(1)).
            subscribe(_ =>  this.stateService.resetNewResources()))).
    unsubscribe();

  }

}
