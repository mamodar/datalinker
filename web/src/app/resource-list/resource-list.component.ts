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
export class ResourceListComponent implements OnDestroy, OnInit {
  private dataSource: MatTableDataSource<Resource>;
  displayedColumns: string[] = ['path', 'location', 'action'];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  sub: Subscription;

  constructor(private stateService: StateService) {
    this.dataSource = new MatTableDataSource<Resource>([]);

  }

  ngOnInit() {

    this.setDataSourceAttributes();

  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  search($event): void {
    this.dataSource.filter = ($event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  private setDataSourceAttributes() {
    // pagination can only be set after the data has been loaded
    // https://github.com/angular/components/issues/10205
    this.sub = this.stateService.getResources().subscribe(
      resourceArray => {
        this.dataSource.data = resourceArray;
        this.dataSource.paginator = this.paginator;
        this.dataSource.filter = '';
      });
  }
}


