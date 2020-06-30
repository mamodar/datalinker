import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-search-search',
  templateUrl: './search-search.component.html',
  styleUrls: ['./search-search.component.css']
})
export class SearchSearchComponent {
  @Output() searchString: EventEmitter<string> = new EventEmitter<string>();
  searchValue: string;

  constructor() {
  }

  onApplySearch(): void {
    this.searchString.emit(this.searchValue);
  }
}
