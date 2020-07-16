import {ChangeDetectorRef, Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {EdocTransfer} from '../../../models/edocTransfer';

@Component({
  selector: 'app-publish-project-dialog',
  templateUrl: './publish-project-dialog.component.html',
  styleUrls: ['./publish-project-dialog.component.css']
})
export class PublishProjectDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: EdocTransfer,
    private ref: ChangeDetectorRef) {
  }

  uploadFile(files: FileList): void {
    console.warn('uploadFile' + JSON.stringify(files.item(0)));
    this.data.file = files.item(0);
  }

  addAuthor(): void {
    this.data.authors = [...this.data.authors, ''];
    this.ref.detectChanges();

  }

  addKeyword(): void {
    this.data.keywords = [...this.data.keywords, ''];
    this.ref.detectChanges();

  }
}
