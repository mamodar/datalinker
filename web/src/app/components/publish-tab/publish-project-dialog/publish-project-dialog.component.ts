import {ChangeDetectorRef, Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {PublicationDTO} from '../../../models/publicationDTO';
import {Author} from '../../../models/author';

@Component({
  selector: 'app-publish-project-dialog',
  templateUrl: './publish-project-dialog.component.html',
  styleUrls: ['./publish-project-dialog.component.css']
})
/**
 * This dialog collects  the metadata for publishing a file to an external service.
 * @author Kyanoush Yahosseini
 */

export class PublishProjectDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: PublicationDTO,
    private ref: ChangeDetectorRef) {
  }

  uploadFile(filesEvent: Event): void {
    const files: FileList = filesEvent[0];
    for (let i = 0; i < files.length; i++) {
      this.data.files.push(files.item(i));
    }
  }

  addAuthor(): void {
    this.data.authors = [...this.data.authors, new Author(null, null)];
    this.ref.detectChanges();

  }

  addKeyword(): void {
    this.data.keywords = [...this.data.keywords, ''];
    this.ref.detectChanges();

  }

  fileSize(file: File[]): number {
    if (file) {
      return file.reduce((previousValue, currentValue) => previousValue + currentValue.size, 0) / 1024 / 1024;
    }
    return 0;
  }

}
