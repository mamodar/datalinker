import {
  AfterViewInit,
  ApplicationRef,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input, OnChanges,
  OnDestroy,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
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
  public dataSource: MatTableDataSource<Resource>;
  public displayedColumns: string[] = ['path', 'location', 'action'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @Input() data$: Observable<Resource[]>;
  sub: Subscription;

  constructor() {
    this.dataSource = new MatTableDataSource<Resource>([]);

  }
  ngOnInit() {
    // pagination can only be set after the data has been loaded
    // https://github.com/angular/components/issues/10205
    this.sub = this.data$.subscribe(
      resourceArray => {
        this.dataSource.data = resourceArray;
        this.dataSource.paginator = this.paginator;
        this.dataSource.filter = '';
      });
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  search($event): void {
    this.dataSource.filter = ($event.target as HTMLInputElement).value.trim().toLowerCase();
  }

}


