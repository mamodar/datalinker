import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {PublicationDTO} from '../../../models/publicationDTO';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {PublishProjectDialogComponent} from '../publish-project-dialog/publish-project-dialog.component';
import {StateService} from '../../../services/state.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {Project} from '../../../models/project';
import {Value} from '../../../models/value';
import {MatSnackBar} from '@angular/material/snack-bar';
import {PublishProjectCheckDialogComponent} from '../publish-project-dialog/publish-project-check-dialog.component';
import {Resource} from '../../../models/resource';
import {ResourceLocation} from '../../../models/resourceLocation';
import {ResourcePath} from '../../../models/resourcePath';

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
  private publishedResource: PublicationDTO;


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
    const warnDialogConfig = new MatDialogConfig();
    warnDialogConfig.disableClose = true;
    warnDialogConfig.minWidth = '50rem';
    warnDialogConfig.maxWidth = '50rem';
    const checkDialog = this.matDialog.open(PublishProjectCheckDialogComponent, warnDialogConfig);
    checkDialog.afterClosed().subscribe(check => {
      if (check) {
        const exportDialogConfig = new MatDialogConfig();
        exportDialogConfig.disableClose = true;
        exportDialogConfig.minWidth = '70%';
        exportDialogConfig.maxWidth = '100%';
        exportDialogConfig.minHeight = '70%';
        exportDialogConfig.maxHeight = '100%';
        const publicationDTO = new PublicationDTO().fromProject(this.selectedProject$.getValue(), this.selectedValues$.getValue());
        exportDialogConfig.data = publicationDTO;
        const modalDialog = this.matDialog.open(PublishProjectDialogComponent, exportDialogConfig);
        modalDialog.afterClosed().subscribe(data => {
          this.publishedResource = data.resource;
          if (data.send) {
            this.sendToPublishInExternal(publicationDTO);
          }
        });
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
    if ((event.status >= 200) && (event.status < 300)) {
      this.openSnackBar('Ihre Veröffentlichung war erfolgreich!');
      this.createNewResourceFromPublication(event.body).subscribe(_ => {
        window.open(event.body.url, '_blank');
        this.stateService.getResources();
      });

    } else {
      this.openSnackBar('Fehler! Veröffentlichung abgebrochen.');
    }
  }

  private openSnackBar(message): void {
    this.snackBar.open(message, 'Schließen', {
      panelClass: ['mat-toolbar', 'snackbar-inv'],
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  private createNewResourceFromPublication(body: any): Observable<Resource> {
    const resource: Resource = new Resource();
    if (body.doi) {
      resource.location = new ResourceLocation('DOI');
      resource.path = new ResourcePath().updateFromValue(body.doi, resource.location);
    } else {
      resource.location = new ResourceLocation('URL');
      resource.path = new ResourcePath().updateFromValue(body.url, resource.location);
    }
    resource.type = 'Datensatz';
    resource.license = this.publishedResource.license;
    resource.description = 'Publikation durch DataLinker: ' + this.publishedResource.keywords.join(';');
    return this.stateService.createResource(resource);
  }
}
