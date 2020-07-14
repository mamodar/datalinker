import {Component, OnInit} from '@angular/core';
import {StateService} from '../../services/state.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {Project} from '../../models/project';
import {MatDialog} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

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
  private selectedProject$: BehaviorSubject<Project>;
  selectedRepo: string;
  progress: number;
  progressBarType: string;

  constructor(public matDialog: MatDialog, private snackBar: MatSnackBar, private stateService: StateService) {
  }

  ngOnInit(): void {
    this.projects$ = this.stateService.getProjects();
    this.selectedProject$ = this.stateService.getSelectedProject();
    this.progress = undefined;

  }

}
