import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Resource} from '../../models/resource';
import {Project} from '../../models/project';

@Component({
  selector: 'app-resource-item',
  templateUrl: './resource-item.component.html',
  styleUrls: ['./resource-item.component.css']
})
export class ResourceItemComponent implements OnInit {

  constructor() { }
  @Input() resource: Resource;
  @Input() selected: Resource;
  @Output() filterByResource = new EventEmitter<Resource>();
  ngOnInit() {
  }
  onChecked($event: boolean) {
    if ($event) {
      this.filterByResource.emit(this.resource);
    } else {
      this.filterByResource.emit(undefined);
    }
  }
}
