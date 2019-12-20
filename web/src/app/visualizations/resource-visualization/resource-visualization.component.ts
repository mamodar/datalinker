import {Component, Input, OnInit} from '@angular/core';

import {Resource} from '../../objects/resource';
import {ProjectService} from '../../services/project.service';
import {ResourceService} from '../../services/resource.service';

@Component({
  selector: 'app-resource-visualization',
  templateUrl: './resource-visualization.component.html',
  styleUrls: ['./resource-visualization.component.css']
})
export class ResourceVisualizationComponent implements OnInit {
  @Input() resource: Resource;
  @Input() minimal: boolean;

  constructor(private projectService: ProjectService, private resourceService: ResourceService) {
  }

  ngOnInit() {
  }

}
