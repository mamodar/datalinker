import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Project} from '../../models/project';
import {StateService} from '../../services/state.service';

/**
 * The main component for the new resource navigation tab.
 * @author Kyanoush Yahosseini
 */

@Component({
  selector: 'app-new-resource-tab',
  templateUrl: './new-resource-tab.component.html',
  styleUrls: ['./new-resource-tab.component.css']
})
export class NewResourceTabComponent implements OnInit {
  projects$: Observable<Project[]>;

  constructor(private stateService: StateService) {
  }

  ngOnInit(): void {
    this.projects$ = this.stateService.getProjects();
  }


  gotoRdmo(): void {
    window.open('http://rdmo.h2888668.stratoserver.net/projects/create/', '_blank');
  }
}
