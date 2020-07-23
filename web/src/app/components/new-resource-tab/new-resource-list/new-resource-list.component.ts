import {Component, OnInit, ViewChild} from '@angular/core';
import {StateService} from '../../../services/state.service';
import {Observable} from 'rxjs';
import {Resource} from '../../../models/resource';
import {MatTable} from '@angular/material/table';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ResourceManipulateDialogComponent} from '../../shared/resource-list/resource-manipulate-dialog/resource-manipulate-dialog.component';

/**
 * This component shows all new resources as an expansion panel.
 * @author Kyanoush Yahosseini
 */

@Component({
  selector: 'app-new-resource-list',
  templateUrl: './new-resource-list.component.html',
  styleUrls: ['./new-resource-list.component.css']
})
export class NewResourceListComponent implements OnInit {

  @ViewChild('table', { static: false }) table: MatTable<any>;

  constructor(private stateService: StateService, public matDialog: MatDialog) {
  }

  newResources$: Observable<Resource[]>;
  ngOnInit() {
    this.newResources$ = this.stateService.getNewShownResources();
  }

  onSelect(resource: Resource) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = resource;
    dialogConfig.disableClose = true;
    dialogConfig.minWidth = '50%';
    dialogConfig.maxWidth = '50%';
    const modalDialog = this.matDialog.open(ResourceManipulateDialogComponent, dialogConfig);

  }
}

