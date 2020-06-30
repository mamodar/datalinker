import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-search-filter',
  templateUrl: './search-filter.component.html',
  styleUrls: ['./search-filter.component.css']
})
export class SearchFilterComponent {
  filterValues: string[] = ['', '', '', '', '', '', '', '', ''];
  filterKeys: string[] = [
    'Speicherort', 'Titel', 'Lizenz', 'Förderer', 'Projektart',
    'Kontaktperson', 'Kooperationspartner (intern)', 'Organisationseinheit', 'Akronym'];

  constructor() {
  }

  @Output() filterString: EventEmitter<string> = new EventEmitter<string>();

  onApplyFilter(): void {
    let stringBuilder = '';
    this.filterValues.forEach((value, index) => {
      if (value !== '') {
        stringBuilder = stringBuilder + this.filterKeys[index] + ':' + value + ',';
      }
    });
    console.log(stringBuilder);
    this.filterString.emit(stringBuilder);
  }
}
