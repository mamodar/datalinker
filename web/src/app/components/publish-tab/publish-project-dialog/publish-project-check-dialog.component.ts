import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {ResourceDeleteButtonComponent} from '../../shared/resource-list/resource-delete-button/resource-delete-button.component';

@Component({
  selector: 'app-publish-project-check-dialog',
  templateUrl: './publish-project-check-dialog.component.html',
  styleUrls: ['./publish-project-check-dialog.component.css']
})
export class PublishProjectCheckDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ResourceDeleteButtonComponent>) {
  }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.dialogRef.close(true);
  }
}
