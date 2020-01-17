import {Component, OnInit} from '@angular/core';
import { Observable} from 'rxjs';
import {Project} from '../models/project';
import {StateService} from '../services/state.service';
import {distinctUntilChanged, tap} from 'rxjs/operators';


@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  constructor() { }
  ngOnInit() {
  }
}
