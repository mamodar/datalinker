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
    this.stateService.getNewShownResources().
    pipe(flatMap(_ => _)).
    subscribe(
      resource => this.stateService.createResource(resource).
      pipe(take(1)).subscribe(_ => {
        this.stateService.resetNewResources();
        this.stateService.getResources();
      }));

  }

}
