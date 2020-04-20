import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Resource} from '../../models/resource';
import {StateService} from '../../services/state.service';
import {ActivatedRoute} from '@angular/router';
import {ResourceEditButtonComponent} from './resource-edit-button.component';
import {filter} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {ResourceType} from '../../models/resourceType';

@Component({
  selector: 'app-resource-edit-dialog',
  templateUrl: './resource-edit-dialog.component.html',
  styleUrls: ['./resource-edit-dialog.component.css']
})
export class ResourceEditDialogComponent implements OnInit {
  resource: Resource;
  dataBackup: Resource;
  resourceTypes$: Observable<ResourceType[]>;
  constructor(    public dialogRef: MatDialogRef<ResourceEditButtonComponent>,
                  @Inject(MAT_DIALOG_DATA) public data: Resource, public stateService : StateService) {
    this.dataBackup = JSON.parse(JSON.stringify(data));
    this.resourceTypes$ = this.stateService.getResourceTypes();
  }

  ngOnInit(): void {

  }

  onNoClick(): void {

    this.dialogRef.close();
  }

  save(): Resource {
    this.data = this.dataBackup;
    return this.dataBackup;
  }
  setSelectedLocation() {
    console.log(this.dataBackup.location);
    this.dataBackup.location = new ResourceType(this.dataBackup.location.value);
    console.log(this.dataBackup.location);
  }

}
