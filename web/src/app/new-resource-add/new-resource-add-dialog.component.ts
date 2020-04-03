import {Component, Inject, OnInit} from '@angular/core';

import {Resource} from '../models/resource';
import {MatInputModule} from '@angular/material/input';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {StateService} from '../services/state.service';
import {ResourceType} from '../models/resourceType';
import {Observable} from 'rxjs';
import {flatMap} from 'rxjs/operators';


@Component({
  selector: 'app-new-resource-add-dialog',
  templateUrl: './new-resource-add-dialog.component.html',
  styleUrls: ['./new-resource-add-dialog.component.css']
})
export class NewResourceAddDialogComponent implements OnInit{
  private resourceTypes$: Observable<ResourceType[]>;


  constructor(
    public dialogRef: MatDialogRef<NewResourceAddDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Resource, private stateService: StateService) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  isFormValid(): boolean {
    console.log(this.data)
    return !(this.data.location && this.data.path);
  }
  ngOnInit() {
    this.resourceTypes$ = this.stateService.getResourceTypes();
  }
}
