import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {ResourceDeleteButtonComponent} from './resource-delete-button.component';

/**
 * This component generates a simple yes/no dialog.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-resource-delete-dialog',
  templateUrl: './resource-delete-dialog.component.html',
  styleUrls: ['./resource-delete-dialog.component.css']
})
export class ResourceDeleteDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ResourceDeleteButtonComponent>) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
  this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.dialogRef.close(true);
  }
}
