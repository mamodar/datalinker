import {Component, Inject, Input, OnInit} from '@angular/core';
import {Resource} from '../../models/resource';
import {StateService} from '../../services/state.service';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {ResourceDeleteDialogComponent} from './resource-delete-dialog.component';
import {tap} from 'rxjs/operators';


@Component({
  selector: 'app-resource-delete-button',
  templateUrl: './resource-delete-button.component.html',
  styleUrls: ['./resource-delete-button.component.css']
})
export class ResourceDeleteButtonComponent implements OnInit {

  constructor(private stateService: StateService, public dialog: MatDialog) { }

  @Input() parent: Resource;

  ngOnInit(): void {
  }
  deleteResource($event) {
    const dialogRef = this.dialog.open(ResourceDeleteDialogComponent);
    dialogRef.afterClosed().pipe(tap(_ => console.log(_))).subscribe(_ => { if (_) {this.stateService.deleteResource(this.parent); }} );
    // this.stateService.deleteResource(this.parent);
  }
}
