import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Project} from '../../models/project';
import {StateService} from '../../services/state.service';

@Component({
  selector: 'app-project-tab',
  templateUrl: './project-tab.component.html',
  styleUrls: ['./project-tab.component.css']
})
export class ProjectTabComponent implements OnInit {
  projects$: Observable<Project[]>;

  constructor(private stateService: StateService) { }

  ngOnInit(): void {
    this.projects$ = this.stateService.getProjects();

  }

}
