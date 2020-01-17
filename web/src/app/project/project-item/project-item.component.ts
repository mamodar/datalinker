import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Project} from '../../models/project';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {

  constructor() { }

   @Input() project: Project;
   @Input() selected: Project;
   @Output() filterResourcesBy = new EventEmitter<Project>();

  ngOnInit() {
  }

  onChecked($event: boolean) {
    if ($event) {
      this.filterResourcesBy.emit(this.project);
    } else {
      this.filterResourcesBy.emit(undefined);
    }
  }
}
