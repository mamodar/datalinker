import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Resource} from '../../models/resource';
import {StateService} from '../../services/state.service';
import {ActivatedRoute} from '@angular/router';
import {map, take} from 'rxjs/operators';
import {ResourceType} from '../../models/resourceType';
import {Observable} from 'rxjs';
import {ResourceEditButtonComponent} from '../resource-edit-button/resource-edit-button.component';
import {NewResourceAddButtonComponent} from '../../new-resource-add/new-resource-add-button.component';
import {CloudType} from '../../models/cloudType';

@Component({
  selector: 'app-resource-manipulate-dialog',
  templateUrl: './resource-manipulate-dialog.component.html',
  styleUrls: ['./resource-manipulate-dialog.component.css']
})
export class ResourceManipulateDialogComponent implements OnInit {
  resourceTypes$: Observable<ResourceType[]>;
  dataBackup: Resource;
  cloudTypes$: Observable<CloudType[]>;
  constructor(
    public dialogRef: MatDialogRef<NewResourceAddButtonComponent | ResourceEditButtonComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Resource,
    public stateService: StateService,
    private activatedRoute: ActivatedRoute) {
    this.dataBackup = JSON.parse(JSON.stringify(data));
  }

  ngOnInit() {
    this.resourceTypes$ = this.stateService.getResourceTypes();
    this.cloudTypes$ = this.stateService.getCloudTypes();
    this.activatedRoute.queryParamMap.pipe( take(1), map(
      // creates an object from aromatically selecting a location
      _ => {
        if (_.get('path')) {
            this.data.path = _.get('path');
            this.data.location = new ResourceType(_.get('location'));
          }
      })).subscribe();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


// creates an object from manually selecting a location

  setSelectedLocation(): void {
    this.data.location = new ResourceType(this.data.location.value);
  }

}
