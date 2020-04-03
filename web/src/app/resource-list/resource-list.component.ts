import {AfterViewInit, ApplicationRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {Project} from '../models/project';
import {BehaviorSubject, Observable, of, Subscription} from 'rxjs';
import {Resource} from '../models/resource';
import {StateService} from '../services/state.service';
import {tap} from 'rxjs/operators';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-resource-list',
  templateUrl: './resource-list.component.html',
  styleUrls: ['./resource-list.component.css']
})
export class ResourceListComponent implements AfterViewInit, OnDestroy {

  constructor(private stateService: StateService) { }

  displayedColumns: string[] = [ 'path', 'location', 'action'];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  dataSource = new MatTableDataSource<Resource>();
  sub: Subscription;

  ngAfterViewInit() {
    this.sub = this.stateService.getResources().subscribe(resourceArray => {
      this.dataSource.data = resourceArray;
      this.dataSource.paginator = this.paginator;
    });
  }
  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  search($event): void {
    console.log($event)
    this.dataSource.filter = ($event.target as HTMLInputElement).value.trim().toLowerCase();
  }
}
