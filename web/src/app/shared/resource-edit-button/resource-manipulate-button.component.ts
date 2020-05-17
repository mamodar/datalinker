import {Component, Input, OnInit} from '@angular/core';
import {StateService} from '../../services/state.service';
import {Resource} from '../../models/resource';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {take} from 'rxjs/operators';
import {ResourceManipulateDialogComponent} from '../resource-manipulate-dialog/resource-manipulate-dialog.component';

/**
 * This components manipulate a resource.
 * It calls {@link ResourceManipulateDialogComponent} to enter the input.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-resource-manipulate-button',
  templateUrl: './resource-manipulate-button.component.html',
  styleUrls: ['./resource-manipulate-button.component.css']
})
export class ResourceManipulateButtonComponent implements OnInit {
  constructor(private stateService: StateService, public matDialog: MatDialog) { }

  @Input() parent: Resource;

  ngOnInit(): void {
  }

  makeResourceEditable($event: MouseEvent): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.data = this.parent;
    const modalDialog = this.matDialog.open(ResourceManipulateDialogComponent, dialogConfig);
    modalDialog.afterClosed().subscribe(resource => {
      if (resource) {
        this.stateService.updateResource(resource).pipe(take(1)).subscribe(_ => this.stateService.getResources());
      }
    });
  }
}
