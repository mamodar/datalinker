import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-relationship-editor',
  templateUrl: './relationship-editor.component.html',
  styleUrls: ['./relationship-editor.component.css']
})
export class RelationshipEditorComponent implements OnInit {
  @Input() formGroup: FormGroup;

  constructor() {
  }

  ngOnInit(): void {
    this._getPathFromURL();
  }


  private _getPathFromURL() {
    if (new URL(window.location.href).searchParams.get('location')) {
      this.formGroup.get('location').setValue(new URL(window.location.href).searchParams.get('location'));

    }
  }

}
