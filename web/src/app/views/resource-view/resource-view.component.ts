import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Resource} from '../../objects/resource';

@Component({
  selector: 'app-resource-view',
  templateUrl: './resource-view.component.html',
  styleUrls: ['./resource-view.component.css']
})
export class ResourceViewComponent implements OnInit {
  resourceEditor: FormGroup;
  @Input() selectedResource: Resource;

  ngOnInit() {
    this.resourceEditor = new FormGroup({
      userName: new FormControl(''),
      resource: new FormControl('', Validators.required)
    });
  }

  cancelForm() {
    console.log('form deleted');
  }

  onSubmit() {
    console.log('form submitted');
  }

  setSelectedResource($event: Resource): void {
    this.selectedResource = $event;
  }

}
