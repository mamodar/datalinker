import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Project} from '../../objects/project';
import {RelationshipService} from '../../services/relationship.service';
import {Relationship} from '../../objects/relationship';

@Component({
  selector: 'app-project-visualization',
  templateUrl: './project-visualization.component.html',
  styleUrls: ['./project-visualization.component.css']
})
export class ProjectVisualizationComponent implements OnInit {

  @Input() project: Project;
  @Input() relationships: Relationship[];
  @Input() minimal: boolean;
  @Output() relationshipsChange = new EventEmitter<Relationship[]>();

  constructor(private relationshipService: RelationshipService) {
  }

  ngOnInit() {
  }

  deletePath(relationship: Relationship) {
    console.log(relationship.id + relationship.resource.path + relationship.project.projectName);
    this.relationshipService.deleteRelationship(relationship).subscribe(_ => this.relationshipsChange.emit(this.relationships.filter(p => p.id !== relationship.id)));

  }
}
