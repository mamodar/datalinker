import { Component, OnInit } from '@angular/core';
import {ResourceService} from '../services/resource.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {Resource} from '../models/resource';
import {Project} from '../models/project';
import {StateService} from '../services/state.service';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.css']
})
export class ResourceComponent implements OnInit {
  constructor() { }

  ngOnInit() {
  }
}
