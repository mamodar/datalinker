import { Component, OnInit } from '@angular/core';
import {Project} from '../../models/project';
import {BehaviorSubject, Observable, Subject, Subscription} from 'rxjs';
import {StateService} from '../../services/state.service';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-search-tab',
  templateUrl: './search-tab.component.html',
  styleUrls: ['./search-tab.component.css']
})
export class SearchTabComponent implements OnInit {
  projects$: Observable<Project[]>;
  searchTerm$ = new BehaviorSubject<string>('');

  constructor(private stateService: StateService) { }

  ngOnInit(): void {
    this.projects$ = this.stateService.searchProjects(this.searchTerm$);
  }
  onKey($event) {
    this.searchTerm$.next($event.target.value);
  }
}
