import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {PublicationDTO} from '../../../models/publicationDTO';
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

/**
 * The  component contains the logic necessary for publishing  a file and metadata to an external service.
 *
 * @author Kyanoush Yahosseini
 */
export class PublishProcedureComponent implements OnInit {
  public progressBarType: string;
  private selectedProject$: BehaviorSubject<Project>;
  private selectedValues$: BehaviorSubject<Value[]>;
  public progress: number;


  constructor(private stateService: StateService, private matDialog: MatDialog, private snackBar: MatSnackBar) {

    this.selectedProject$ = this.stateService.getSelectedProject();
    this.selectedValues$ = this.stateService.getValuesForSelectedProject();
  }


  @Input() selectedRepo: string;

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
    const publicationDTO = new PublicationDTO().fromProject(this.selectedProject$.getValue(), this.selectedValues$.getValue());
    dialogConfig.data = publicationDTO;
    const modalDialog = this.matDialog.open(PublishProjectDialogComponent, dialogConfig);
    modalDialog.afterClosed().subscribe(_ => {
      if (_.send) {
        this.sendToPublishInExternal(publicationDTO);
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

  private sendToPublishInExternal(publicationDTO: PublicationDTO): void {
    publicationDTO.authors.filter(value => value.name !== '');
    this.progressBarType = 'start';
    this.stateService.publishToExternalService(this.selectedRepo, publicationDTO).subscribe(
      event => {
        this.progressBarType = 'upload';
        this.waitForResponse(event);
      },
      error => {
        console.warn(JSON.stringify(error));
        this.openSnackBar('Fehler!');
        this.progressBarType = 'error';
      });
  }

  private reactToFinishedUpload(event: HttpResponse<any>) {
    this.progressBarType = 'finished';
    console.log(event.status);
    if ((event.status >= 200) && (event.status < 300)) {
      this.openSnackBar('Erfolg!');
    } else {
      this.openSnackBar('Fehler!');
    }
  }

  private openSnackBar(message): void {
    this.snackBar.open(message, null, {
      duration: 6000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}
