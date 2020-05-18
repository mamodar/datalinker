import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {filter, map, take} from 'rxjs/operators';
import {StateService} from '../services/state.service';
import {Resource} from '../models/resource';
import {ActivatedRoute, Router} from '@angular/router';
import {ResourceManipulateDialogComponent} from '../shared/resource-manipulate-dialog/resource-manipulate-dialog.component';
import {ResourceType} from '../models/resourceType';

/**
 * This components contains the create new resource logic.
 * It depends on {@link ResourceManipulateDialogComponent} to create a new resource.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-new-resource-add-button',
  templateUrl: './new-resource-add-button.component.html',
  styleUrls: ['./new-resource-add-button.component.css']
})
export class NewResourceAddButtonComponent implements OnInit {

  constructor(public matDialog: MatDialog, private stateService: StateService,
              private activatedRoute: ActivatedRoute, private router: Router) {
  }

  newResource: Resource;

  ngOnInit() {
    this.activatedRoute.queryParamMap.pipe(take(1), map(_ => _.has('path') ? this.openAddNew() : null)).subscribe();
  }

  openAddNew(): void {
    this.newResource = new Resource();
    this.newResource.location = new ResourceType(null);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.data = this.newResource;
    // https://material.angular.io/components/dialog/overview
    const modalDialog = this.matDialog.open(ResourceManipulateDialogComponent, dialogConfig);
    modalDialog.afterClosed().pipe(filter(_ => !!_)).subscribe(
      _ => {
        if (_.send) {
          this.stateService.addNewResource(_.resource);
          this.router.navigate([], {queryParams: null});
        }
      });
  }
}
