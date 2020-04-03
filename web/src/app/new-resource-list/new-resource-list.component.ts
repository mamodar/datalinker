import { AfterViewInit, Component, OnInit} from '@angular/core';
import {StateService} from '../services/state.service';
import { Observable} from 'rxjs';
import {Resource} from '../models/resource';
import { ViewChild } from '@angular/core';
import {MatTable} from '@angular/material/table';

@Component({
  selector: 'app-new-resource-list',
  templateUrl: './new-resource-list.component.html',
  styleUrls: ['./new-resource-list.component.css']
})
export class NewResourceListComponent implements OnInit, AfterViewInit {

  @ViewChild('table', { static: false }) table: MatTable<any>;

  constructor(private stateService: StateService) {
  }

  newResources$: Observable<Resource[]>;
  ngOnInit() {
  }
  ngAfterViewInit() {
    this.newResources$ = this.stateService.getNewShownResources().pipe();
  }
}

