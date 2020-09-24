import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {Resource} from '../../../models/resource';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {Router} from '@angular/router';

/**
 * This component shows all resources of a project as a table.
 * It allows manipulation of resources by containing {@link ResourceDeleteButtonComponent} and {@link ResourceManipulateButtonComponent} .
 * @author Kyanoush Yahosseini
 */

@Component({
  selector: 'app-resource-list',
  templateUrl: './resource-list.component.html',
  styleUrls: ['./resource-list.component.css']
})

export class ResourceListComponent implements OnDestroy, OnInit {

  public dataSource: MatTableDataSource<Resource>;
  public displayedColumns: string[];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @Input() data$: Observable<Resource[]>;
  sub: Subscription;

  constructor(readonly router: Router) {
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
        this.dataSource.filterPredicate = (data, filter) =>
          JSON.stringify(data).trim().toLocaleLowerCase().includes(filter.trim().toLocaleLowerCase());
      });
    if (this.router.url === '/projects') {
      this.displayedColumns = ['description', 'location', 'path', 'type', 'license', 'action'];
    } else {
      this.displayedColumns = ['description', 'location', 'path', 'type', 'license'];
    }
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

}


