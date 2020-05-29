import {Component, Inject, OnInit} from '@angular/core';
import {StateService} from '../services/state.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {Project} from '../models/project';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {NewResourceAddButtonComponent} from '../new-resource-add/new-resource-add-button.component';
import {EdocTransfer} from '../models/edocTransfer';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';

/**
 * The main component for the publish tab.
 * @author Kyanoush Yahosseini
 */

@Component({
  selector: 'app-publish-tab',
  templateUrl: './publish-tab.component.html',
  styleUrls: ['./publish-tab.component.css']
})
export class PublishTabComponent implements OnInit {
  projects$: Observable<Project[]>;
  private selectedProject$: BehaviorSubject<Project>;
  selectedRepo: string;
  progress: number;

  constructor(public matDialog: MatDialog, private snackBar: MatSnackBar, private stateService: StateService) {
  }

  ngOnInit(): void {
    this.projects$ = this.stateService.getProjects();
    this.selectedProject$ = this.stateService.getSelectedProject();
    this.progress = undefined;

  }

  exportPossible(): boolean {
    return !((this.selectedProject$.getValue() !== undefined) && (this.selectedRepo !== undefined));
  }

  openExportDialog(): void {
    this.progress = undefined;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    const edocTransfer = new EdocTransfer();
    edocTransfer.fromProject(this.selectedProject$.getValue());
    console.warn(edocTransfer);
    dialogConfig.data = edocTransfer;
    const modalDialog = this.matDialog.open(ProjectPublishDialogComponent, dialogConfig);
    modalDialog.afterClosed().subscribe(_ => {
      if (_.send) {
        this.stateService.publishToExternalService('edoc', edocTransfer).pipe().subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            // This is an upload progress event. Compute the % done:
            this.progress = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            // server has responded
            if (event.status === 200) {
              this.openSnackBar('Erfolg!');
              this.progress = 101;
            } else {
              this.openSnackBar('Fehler!');
            }
          }
        });
      }
    });

  }

  private openSnackBar(message): void {
    this.snackBar.open(message, null, {
      duration: 1000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}


@Component({
  selector: 'app-resource-manipulate-dialog',
  templateUrl: './project-publish-dialog.component.html',
})
export class ProjectPublishDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<NewResourceAddButtonComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EdocTransfer) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  uploadFile(files: any) {
    console.log(files[0]);
    this.data.file = files[0];
  }
}
