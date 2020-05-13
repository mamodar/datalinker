import {
  Component,
  Input,
  OnDestroy,
  OnInit,

  ViewChild
} from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {Resource} from '../models/resource';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {Router} from '@angular/router';
import {CloudType} from '../models/cloudType';


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
      });
    if (this.router.url === '/projects') {
      this.displayedColumns = ['path', 'location', 'action'];
    } else {
      this.displayedColumns = ['path', 'location'];
    }
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  search($event): void {
    this.dataSource.filter = ($event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  newCloudValue(path: string): string {
    return new CloudType(path).viewValue;
  }

}


