import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, Subscription} from 'rxjs';
import {Project} from '../../../models/project';
import {distinctUntilChanged, map} from 'rxjs/operators';

@Component({
  selector: 'app-project-list-paginate',
  templateUrl: './project-list-paginate.component.html',
  styleUrls: ['./project-list-paginate.component.css']
})
/**
 * This component combines {@link ProjectListComponent} and {@link PaginatorComponent} for a project list with a paginator.
 * @author Kyanoush Yahosseini
 */
export class ProjectListPaginateComponent implements OnInit, OnDestroy {
  @Input() projects$: BehaviorSubject<Project[]>;

  constructor() {
  }

  projectsPaginated$: BehaviorSubject<Project[]>;
  private sub: Subscription;

  ngOnInit(): void {
    this.projectsPaginated$ = new BehaviorSubject<Project[]>(null);
  }

  paginatorChanged($event: Observable<Project[]>): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
    this.sub = $event.pipe(distinctUntilChanged(), map(value => {
      this.projectsPaginated$.next(value);
    })).subscribe();
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }
}
