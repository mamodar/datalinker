import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {filter, map, take} from 'rxjs/operators';
import {StateService} from '../../../services/state.service';
import {Resource} from '../../../models/resource';
import {ActivatedRoute, Router} from '@angular/router';
// tslint:disable-next-line:max-line-length
import {ResourceManipulateDialogComponent} from '../../shared/resource-list/resource-manipulate-dialog/resource-manipulate-dialog.component';
import {ResourceLocation} from '../../../models/resourceLocation';
import {Subscription} from 'rxjs';

/**
 * This components contains the create new resource logic.
 * It depends on {@link ResourceManipulateDialogComponent} to create a new resource.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-new-resource-add',
  templateUrl: './new-resource-add.component.html',
  styleUrls: ['./new-resource-add.component.css']
})
export class NewResourceAddComponent implements OnInit, OnDestroy {

  constructor(public matDialog: MatDialog, private stateService: StateService,
              private activatedRoute: ActivatedRoute, private router: Router) {
  }

  newResource: Resource;
  sub: Subscription;

  ngOnInit() {
    this.activatedRoute.queryParamMap.pipe(take(1), map(_ => _.has('path') ? this.openAddNew() : null)).subscribe();
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  projectSelected(): boolean {
    return this.stateService.getSelectedProject().getValue() === undefined;
  }

  openAddNew(): void {
    this.newResource = new Resource();
    this.newResource.location = new ResourceLocation(null);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '50%';
    dialogConfig.maxWidth = '50%';
    dialogConfig.disableClose = true;
    dialogConfig.data = this.newResource;
    // https://material.angular.io/components/dialog/overview
    const modalDialog = this.matDialog.open(ResourceManipulateDialogComponent, dialogConfig);
    modalDialog.afterClosed().pipe(filter(_ => !!_)).subscribe(
      data => {
        if (data.send) {
          if (this.sub) {
            this.sub.unsubscribe();
          }
          this.sub = this.stateService.createResource(data.resource).subscribe(_ => {
            this.router.navigate([], {queryParams: null});
            this.stateService.getResources();
          });
        }
      });
  }
}
