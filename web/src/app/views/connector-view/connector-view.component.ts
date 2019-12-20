import {AfterViewInit, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Project} from '../../objects/project';
import {ProjectService} from '../../services/project.service';
import {ResourceService} from '../../services/resource.service';
import {RelationshipService} from '../../services/relationship.service';
import {Resource} from '../../objects/resource';
import {Relationship} from '../../objects/relationship';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-connector-view',
  templateUrl: './connector-view.component.html',
  styleUrls: ['./connector-view.component.css']
})

export class ConnectorViewComponent implements OnInit, AfterViewInit {
  @Input() selectedProject: Project;
  @Input() selectedResource: Resource;
  projectRelationships$: Observable<Relationship[]>;
  resourceRelationships$: Observable<Relationship[]>;

  constructor(private projectService: ProjectService,
              private resourceService: ResourceService,
              private relationshipService: RelationshipService,
              private changeDetectorRef: ChangeDetectorRef) {

  }

  projectPathConnectionEditor: FormGroup;

  ngAfterViewInit() {

  }

  ngOnInit() {
    this.projectPathConnectionEditor = new FormGroup({
      project: new FormControl('', Validators.required),
      userName: new FormControl(),
      resource: new FormControl(''),
      location: new FormControl('', Validators.required),
      datatype: new FormControl()
    });
  }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    const newResource: Resource = {
      id: null,
      date: new Date(),
      location: this.projectPathConnectionEditor.get('location').value,
      path: this.projectPathConnectionEditor.get('resource').value,
      datatype: this.projectPathConnectionEditor.get('datatype').value,
    };

    if (!this.selectedResource) {
      this.resourceService.addResource(newResource).subscribe(p =>
        this.relationshipService.addRelationship(this.selectedProject, p).subscribe());
    } else {
      this.relationshipService.addRelationship(this.selectedProject, this.selectedResource).subscribe();
    }

    this.changeDetectorRef.detectChanges();
  }


  setSelectedProject($event: Project): void {
    console.log('event recieved:' + $event.projectName);
    if ($event.projectName) {
      this.selectedProject = $event;
      this.projectRelationships$ = this.relationshipService.getRelationshipsOfProject(this.selectedProject);
    } else {
      this.selectedProject = undefined;
    }
  }

  setSelectedResource($event: Resource): void {
    console.log('event recieved:' + $event.path);
    if ($event.path) {
      this.projectPathConnectionEditor.get('location').setValue($event.location);
      this.selectedResource = $event;
    } else {
      this.selectedResource = undefined;
    }

  }
}
