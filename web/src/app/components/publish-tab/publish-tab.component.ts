import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {StateService} from '../../services/state.service';
import {Observable} from 'rxjs';
import {Project} from '../../models/project';

/**
 * The main component for the publish tab.
 * @author Kyanoush Yahosseini
 */

@Component({
  selector: 'app-publish-tab',
  templateUrl: './publish-tab.component.html',
  styleUrls: ['./publish-tab.component.css']
})
export class PublishTabComponent implements OnInit {
  projects$: Observable<Project[]>;
  selectedRepo: string;
  @Output() selectedRepoEmitter: EventEmitter<string> = new EventEmitter<string>();

  constructor(private stateService: StateService) {
  }

  ngOnInit(): void {
    this.projects$ = this.stateService.getProjects();
  }

  emitSelectedRepo() {
    this.selectedRepoEmitter.emit(this.selectedRepo);
  }
}

