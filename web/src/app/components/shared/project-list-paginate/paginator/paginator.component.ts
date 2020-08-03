import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Project} from '../../../../models/project';
import {PageEvent} from '@angular/material/paginator/paginator';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
/**
 * This component adds pagination to a {@link ProjectListComponent}.
 *
 * @author Kyanoush Yahosseini
 */
export class PaginatorComponent implements OnInit {
  @Input() projects$: BehaviorSubject<Project[]>;
  @Output() projectEmitter: EventEmitter<Observable<Project[]>> = new EventEmitter<Observable<Project[]>>();
  pageSize = 10;

  constructor() {
  }

  @ViewChild('paginator', {static: true}) paginator;

  ngOnInit(): void {
    // the basic condition
    this.updateProjects({pageSize: this.pageSize, pageIndex: 0, length: null, previousPageIndex: null});
  }

  updateProjects(event: PageEvent): void {
    // if project is undefined (API call hasn't went trough) emit null
    // otherwise emit the projects
    this.projectEmitter.emit(this.projects$.pipe(
      map(value => value ? value.filter((value1, index) =>
        index < ((event.pageIndex + 1) * event.pageSize) &&
        index >= ((event.pageIndex) * event.pageSize)) : null)));
  }
}
