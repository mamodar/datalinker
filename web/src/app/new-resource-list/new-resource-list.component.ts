import {AfterViewInit, Component, OnInit} from '@angular/core';
import {StateService} from '../services/state.service';
import {Observable} from 'rxjs';
import {Resource} from '../models/resource';
import {ViewChild} from '@angular/core';
import {MatTable} from '@angular/material/table';
import {CloudType} from '../models/cloudType';

/**
 * This component shows all new resources as an expansion panel.
 * @author Kyanoush Yahosseini
 */

@Component({
  selector: 'app-new-resource-list',
  templateUrl: './new-resource-list.component.html',
  styleUrls: ['./new-resource-list.component.css']
})
export class NewResourceListComponent implements OnInit {

  @ViewChild('table', { static: false }) table: MatTable<any>;

  constructor(private stateService: StateService) {
  }

  newResources$: Observable<Resource[]>;
  ngOnInit() {
    this.newResources$ = this.stateService.getNewShownResources();
  }

}

