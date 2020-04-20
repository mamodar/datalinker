import {Component, Inject, OnInit} from '@angular/core';

import {Resource} from '../models/resource';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {StateService} from '../services/state.service';
import {ResourceType} from '../models/resourceType';
import {Observable} from 'rxjs';
import { map, take} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';


@Component({
  selector: 'app-new-resource-add-dialog',
  templateUrl: './new-resource-add-dialog.component.html',
  styleUrls: ['./new-resource-add-dialog.component.css']
})
export class NewResourceAddDialogComponent implements OnInit {
  resourceTypes$: Observable<ResourceType[]>;


  constructor(
    public dialogRef: MatDialogRef<NewResourceAddDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Resource,
    public stateService: StateService,
    private activatedRoute: ActivatedRoute) {}

  onNoClick(): void {

    this.dialogRef.close();
  }

  isFormValid(): boolean {
    return !(this.data.location && this.data.path);
  }
  ngOnInit() {
    this.resourceTypes$ = this.stateService.getResourceTypes();
    this.activatedRoute.queryParamMap.pipe( take(1), map(
      // creates an object from aromatically selecting a location
      _ => {this.data.path = _.get('path'); this.data.location = new ResourceType(_.get('location')); } )).subscribe();
  }
// creates an object from manually selecting a location
  setSelectedLocation() {
    console.log(this.data.location)
    this.data.location = new ResourceType(this.data.location.value);
    console.log(this.data.location)
  }
}
