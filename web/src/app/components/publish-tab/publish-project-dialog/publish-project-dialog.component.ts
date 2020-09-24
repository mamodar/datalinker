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
    // @ts-ignore
    const files: FileList = filesEvent.target.files;
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

  removeAuthor(): void {
    this.data.authors = this.data.authors.slice(0, this.data.authors.length - 1);
    this.ref.detectChanges();

  }

  removeKeyword() {
    this.data.keywords = this.data.keywords.slice(0, this.data.keywords.length - 1);
    this.ref.detectChanges();
  }
}
