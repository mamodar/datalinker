import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../../objects/project';
import {RelationshipService} from '../../services/relationship.service';
import {Relationship} from '../../objects/relationship';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-project-visualization',
  templateUrl: './project-visualization.component.html',
  styleUrls: ['./project-visualization.component.css']
})
export class ProjectVisualizationComponent implements OnInit {

  @Input() project: Project;
  @Input() relationships$: Observable<Relationship[]>;
  @Input() minimal: boolean;

  constructor(private relationshipService: RelationshipService) {
  }

  ngOnInit() {
  }

  deletePath(relationship: Relationship) {
    console.log(relationship.resource.path + relationship.project.projectName);
    this.relationshipService.deleteRelationship(relationship).subscribe();
  }
}
