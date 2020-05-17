import {Component, Input} from '@angular/core';
import {Resource} from '../../models/resource';
import {StateService} from '../../services/state.service';
import {MatDialog} from '@angular/material/dialog';
import {ResourceDeleteDialogComponent} from './resource-delete-dialog.component';

/**
 * This components deletes a resource.
 * It uses {@link ResourceDeleteDialogComponent} to verify the input.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-resource-delete-button',
  templateUrl: './resource-delete-button.component.html',
  styleUrls: ['./resource-delete-button.component.css']
})

export class ResourceDeleteButtonComponent {

  constructor(private stateService: StateService, public dialog: MatDialog) { }

  @Input() parent: Resource;

  deleteResource($event) {
    const dialogRef = this.dialog.open(ResourceDeleteDialogComponent);
    dialogRef.afterClosed().subscribe(_ => { if (_) {this.stateService.deleteResource(this.parent); }} );
  }
}
