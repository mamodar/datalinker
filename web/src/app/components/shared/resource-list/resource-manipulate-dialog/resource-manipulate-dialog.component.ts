import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Resource} from '../../../../models/resource';
import {StateService} from '../../../../services/state.service';
import {ActivatedRoute} from '@angular/router';
import {map, take} from 'rxjs/operators';
import {ResourceType} from '../../../../models/resourceType';
import {Observable} from 'rxjs';
import {NewResourceAddComponent} from '../../../new-resource-tab/new-resource-add/new-resource-add.component';
import {ResourcePath} from '../../../../models/resourcePath';

/**
 * This component is a dialog which allows to manipulate or show details to a component.
 * Depending on the route manipulation is either allowed or disallowed.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-resource-manipulate-dialog',
  templateUrl: './resource-manipulate-dialog.component.html',
  styleUrls: ['./resource-manipulate-dialog.component.css']
})
export class ResourceManipulateDialogComponent implements OnInit {
  resourceTypes$: Observable<ResourceType[]>;
  dataBackup: Resource;
  constructor(
    public dialogRef: MatDialogRef<NewResourceAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Resource,
    public stateService: StateService,
    private activatedRoute: ActivatedRoute) {
    this.dataBackup = JSON.parse(JSON.stringify(data));
  }

  ngOnInit() {
    this.resourceTypes$ = this.stateService.getResourceTypes();
    if (!this.data.path) {this.data.path = new ResourcePath(); }
    this.activatedRoute.queryParamMap.pipe( take(1), map(
      // creates an object from aromatically selecting a location
      _ => {
        if (_.get('path')) {
          this.data.location = new ResourceType(_.get('location'));
          this.data.path.updateFromViewValue(_.get('path'), this.data.location);
        }
      })).subscribe();
  }

// creates an object from manually selecting a location

  setSelectedLocation(): void {
    this.data.path = new ResourcePath();
    this.data.location = new ResourceType(this.data.location.value);
  }

  changedResourcePath(event: any): void {
    if (!event.target) {
      this.data.path.updateFromViewValue(event.value, this.data.location);
    } else {
      this.data.path.updateFromViewValue(event.target.value, this.data.location);
    }
  }
}
