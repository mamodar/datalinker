import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-search-search',
  templateUrl: './search-search.component.html',
  styleUrls: ['./search-search.component.css']
})
/**
 * This component contains the search a project logic.
 * @author Kyanoush Yahosseini
 */
export class SearchSearchComponent {
  @Output() searchString: EventEmitter<string> = new EventEmitter<string>();
  searchValue: string;

  constructor() {
  }

  onApplySearch(): void {
    this.searchString.emit(this.searchValue);
  }
}
