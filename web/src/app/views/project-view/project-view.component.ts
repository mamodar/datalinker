import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Project} from '../../objects/project';

@Component({
  selector: 'app-project-view',
  templateUrl: './project-view.component.html',
  styleUrls: ['./project-view.component.css']
})
export class ProjectViewComponent implements OnInit {
  projectEditor: FormGroup;
  @Input() selectedProject: Project;

  ngOnInit() {
    this.projectEditor = new FormGroup({
      userName: new FormControl(''),
      project: new FormControl('', Validators.required)
    });
  }

  cancelForm() {
    console.log('form deleted');
  }

  onSubmit() {
    console.log('form submitted');
  }


  setSelectedProject($event: Project) {
    if ($event.projectName) {
      this.selectedProject = $event;
    } else {
      this.selectedProject = undefined;
    }
  }
}
