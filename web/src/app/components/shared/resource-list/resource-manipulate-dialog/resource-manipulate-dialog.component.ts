import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Resource} from '../../../../models/resource';
import {StateService} from '../../../../services/state.service';
import {ResourceLocation} from '../../../../models/resourceLocation';
import {Observable} from 'rxjs';
import {NewResourceAddComponent} from '../../../new-resource-tab/new-resource-add/new-resource-add.component';
import {ResourcePath} from '../../../../models/resourcePath';
import {TypeService} from '../../../../services/type.service';

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
  resourceLocations$: Observable<ResourceLocation[]>;
  dataBackup: Resource;
  @ViewChild('newResourceForm') newResourceForm: any; // Initialize the ref of input element
  constructor(
    public dialogRef: MatDialogRef<NewResourceAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Resource,
    public stateService: StateService,
    public typeService: TypeService) {
    this.dataBackup = JSON.parse(JSON.stringify(data));
  }

  ngOnInit() {
    this.resourceLocations$ = this.typeService.getResourceLocations();
    if (!this.data.path) {
      this.data.path = new ResourcePath();
    }
  }

  setSelectedLocation(): void {
    this.data.path = new ResourcePath();
    this.data.location = new ResourceLocation(this.data.location.value);
    this.data.path.value = this.data.location.pathDefault;
    this.data.path.viewValue = this.data.location.pathDefault;
  }

  changedResourcePath(event: any): void {

    if (!event.target) {
      this.data.path.updateFromViewValue(event.value, this.data.location);
    } else {
      this.data.path.updateFromViewValue(event.target.value, this.data.location);
    }
  }

  onClose(data: { resource: Resource; send: boolean }) {
    this.newResourceForm.form.markAllAsTouched();
    if (this.newResourceForm.form.status === 'VALID' || !data.send) {
      this.dialogRef.close(data);
    }
  }
}
