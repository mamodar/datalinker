import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {EdocTransfer} from '../../../models/edocTransfer';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {PublishProjectDialogComponent} from '../publish-project-dialog/publish-project-dialog.component';
import {StateService} from '../../../services/state.service';
import {BehaviorSubject} from 'rxjs';
import {Project} from '../../../models/project';
import {Value} from '../../../models/value';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-publish-procedure',
  templateUrl: './publish-procedure.component.html',
  styleUrls: ['./publish-procedure.component.css']
})
export class PublishProcedureComponent implements OnInit {
  public progressBarType: string;
  private selectedProject$: BehaviorSubject<Project>;
  private selectedValues$: BehaviorSubject<Value[]>;
  public progress: number;


  constructor(private stateService: StateService, private matDialog: MatDialog, private snackBar: MatSnackBar) {

    this.selectedProject$ = this.stateService.getSelectedProject();
    this.selectedValues$ = this.stateService.getValuesForSelectedProject();
  }

  // tslint:disable-next-line:no-input-rename
  @Input('repo') selectedRepo: string;

  ngOnInit(): void {
  }

  exportPossible(): boolean {
    return !((this.selectedProject$.getValue() !== undefined) && (this.selectedRepo !== undefined));
  }

  openExportDialog(): void {
    this.progressBarType = 'hidden';
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.minWidth = '50%';
    dialogConfig.maxWidth = '50%';
    const edocTransfer = new EdocTransfer().fromProject(this.selectedProject$.getValue(), this.selectedValues$.getValue());
    dialogConfig.data = edocTransfer;
    const modalDialog = this.matDialog.open(PublishProjectDialogComponent, dialogConfig);
    modalDialog.afterClosed().subscribe(_ => {
      if (_.send) {
        this.sendToPublishInExternal(edocTransfer);
      }
    });

  }


  private waitForResponse(event: any): void {
    if (event.type === HttpEventType.UploadProgress) {
      // This is an upload progress event. Compute the % done:
      this.progress = Math.round(100 * event.loaded / event.total);
      // If upload is finished, the backend needs to handle the publication
      if (this.progress >= 100) {
        this.progressBarType = 'server';
      }
    } else if (event instanceof HttpResponse) {
      // server has responded
      this.reactToFinishedUpload(event);
    }
  }

  private sendToPublishInExternal(edocTransfer: EdocTransfer): void {
    edocTransfer.authors.filter(value => value !== '');
    this.progressBarType = 'start';
    this.stateService.publishToExternalService('edoc', edocTransfer).pipe().subscribe(event => {
      this.progressBarType = 'upload';
      this.waitForResponse(event);
    });
  }

  private reactToFinishedUpload(event: HttpResponse<any>) {
    this.progressBarType = 'finished';
    if (event.status === 200) {
      this.openSnackBar('Erfolg!');
    } else {
      this.openSnackBar('Fehler!');
    }
  }

  private openSnackBar(message): void {
    this.snackBar.open(message, null, {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}
