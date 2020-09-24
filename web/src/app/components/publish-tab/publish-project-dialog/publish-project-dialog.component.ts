import {ChangeDetectorRef, Component, Inject, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {PublicationDTO} from '../../../models/publicationDTO';
import {Author} from '../../../models/author';
import {TypeService} from '../../../services/type.service';

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
  @ViewChild('publishForm') publishForm: any; // Initialize the ref of input element
  showFileUploadError = false;


  constructor(
    public dialogRef: MatDialogRef<PublishProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PublicationDTO,
    private ref: ChangeDetectorRef,
    public typeService: TypeService) {
  }

  uploadFile(filesEvent: Event): void {
    // @ts-ignore
    const files: FileList = filesEvent.target.files;
    for (let i = 0; i < files.length; i++) {
      this.data.files.push(files.item(i));
    }
  }




  fileSize(file: File[]): number {
    if (file) {
      return file.reduce((previousValue, currentValue) => previousValue + currentValue.size, 0) / 1024 / 1024;
    }
    return 0;
  }

  addAuthor(index: number): void {
    this.data.authors = [
      ...this.data.authors.slice(0, index + 1),
      new Author(null, null),
      ...this.data.authors.slice(index + 1, this.data.authors.length)];
    this.ref.detectChanges();
  }

  removeAuthor(index: number): void {
    this.data.authors = [...this.data.authors.slice(0, index),
      ...this.data.authors.slice(index + 1, this.data.authors.length)];
    this.ref.detectChanges();

  }

  addKeyword(index: number): void {
    this.data.keywords = [
      ...this.data.keywords.slice(0, index + 1),
      '',
      ...this.data.keywords.slice(index + 1, this.data.keywords.length)];
    this.ref.detectChanges();

  }

  removeKeyword(index: number) {
    this.data.keywords = [...this.data.keywords.slice(0, index),
      ...this.data.keywords.slice(index + 1, this.data.keywords.length)];
    this.ref.detectChanges();
  }

  onClose(data: { resource: PublicationDTO; send: boolean }) {
    this.publishForm.form.markAllAsTouched();
    if (!data.resource.files ||
      (data.resource.files.length <= 0) ||
      (this.fileSize(data.resource.files) > 50)) {
      this.showFileUploadError = true;
    }
    if ((this.publishForm.form.status === 'VALID' &&
      data.resource.files &&
      (data.resource.files.length > 0) &&
      (this.fileSize(data.resource.files) < 50))
      || !data.send) {
      this.dialogRef.close(data);
    }
  }
}
