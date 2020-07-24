import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../../models/project';
import {BehaviorSubject, of, Subscription} from 'rxjs';
import {StateService} from '../../services/state.service';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-search-tab',
  templateUrl: './search-tab.component.html',
  styleUrls: ['./search-tab.component.css']
})
/**
 * The main component for the search navigation tab.
 * @author Kyanoush Yahosseini
 */
export class SearchTabComponent implements OnInit {
  projects$: BehaviorSubject<Project[]> = new BehaviorSubject<Project[]>(null);
  @Input() searchTerm: string;
  @Input() filterTerm: string;
  searchTermSave: string;
  filterTermSave: string;
  public showFilter: boolean;
  sub: Subscription;
  constructor(private stateService: StateService) {
  }

  ngOnInit(): void {
    this.showFilter = false;
    this.sub = this.stateService.searchProjects(of(null), of(null)).pipe(map(value => this.projects$.next(value))).subscribe();
  }

  addSearchTerm($event: string): void {
    this.searchTermSave = $event;
    if (this.sub) {
      this.sub.unsubscribe();
    }

    this.sub = this.stateService.searchProjects(of($event), of(this.filterTermSave)).pipe(
      map(value => this.projects$.next(value))).subscribe();

  }

  addFilterTerm($event: string): void {

    this.filterTermSave = $event;
    if (this.sub) {
      this.sub.unsubscribe();
    }
    this.sub = this.stateService.searchProjects(of(this.searchTermSave), of($event)).pipe(
      map(value => this.projects$.next(value))).subscribe();
  }

  toggleFilter(): void {
    this.showFilter = !this.showFilter;
  }
}
