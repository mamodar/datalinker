import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../models/project';
import {Observable, of} from 'rxjs';
import {StateService} from '../services/state.service';

@Component({
  selector: 'app-search-tab',
  templateUrl: './search-tab.component.html',
  styleUrls: ['./search-tab.component.css']
})
export class SearchTabComponent implements OnInit {
  projects$: Observable<Project[]>;
  @Input() searchTerm: string;
  @Input() filterTerm: string;
  searchTermSave: string;
  filterTermSave: string;
  public showFilter: boolean;

  constructor(private stateService: StateService) {
  }

  ngOnInit(): void {
    this.showFilter = false;
    this.projects$ = this.stateService.getProjects();
  }

  addSearchTerm($event: string): void {
    this.searchTermSave = $event;
    this.projects$ = this.stateService.searchProjects(of($event), of(this.filterTermSave));
  }

  addFilterTerm($event: string): void {

    this.filterTermSave = $event;
    this.projects$ = this.stateService.searchProjects(of(this.searchTermSave), of($event));
  }

  toggleFilter(): void {
    this.showFilter = !this.showFilter;
  }
}
