import {Component, Inject, OnInit} from '@angular/core';
import {StateService} from '../services/state.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {Project} from '../models/project';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {NewResourceAddButtonComponent} from '../new-resource-add/new-resource-add-button.component';
import {EdocTransfer} from '../models/edocTransfer';
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
  constructor(  public matDialog: MatDialog, private stateService: StateService) { }
  ngOnInit(): void {
    this.projects$ = this.stateService.getProjects();
    this.selectedProject$ = this.stateService.getSelectedProject();

  }
  exportPossible(): boolean {
    return !((this.selectedProject$.getValue() !== undefined) && (this.selectedRepo !== undefined));
  }
  openExportDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    const edocTransfer = new EdocTransfer();
    edocTransfer.fromProject(this.selectedProject$.getValue());
    dialogConfig.data = edocTransfer;
    const modalDialog = this.matDialog.open(ProjectPublishDialogComponent, dialogConfig);
    modalDialog.afterClosed().subscribe(_ => {this.selectedRepo = undefined; } )
  }
}


@Component({
  selector: 'app-resource-manipulate-dialog',
  templateUrl: './project-publish-dialog.component.html',
})
export class ProjectPublishDialogComponent  {
  constructor(
    public dialogRef: MatDialogRef<NewResourceAddButtonComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EdocTransfer) {

  }
  onNoClick(): void {
    this.dialogRef.close();
  }

  uploadFile(files: any) {

  }
}
