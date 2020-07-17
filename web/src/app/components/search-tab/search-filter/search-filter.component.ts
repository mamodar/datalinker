import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {StateService} from '../../../services/state.service';
import {Value} from '../../../models/value';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-search-filter',
  templateUrl: './search-filter.component.html',
  styleUrls: ['./search-filter.component.css']
})
/**
 * This component allows to filter projects depending on their metadata fields.
 * @author Kyanoush Yahosseini
 */
export class SearchFilterComponent implements OnInit {
  filterValues: string[] = ['', '', '', '', '', '', '', '', ''];
  filterKeys: string[] = [
    'Speicherort', 'Titel', 'Lizenz', 'Förderer', 'Projektart',
    'Kontaktperson', 'Kooperationspartner (intern)', 'Organisationseinheit', 'Akronym'];
  allValues: Value[] = [];
  @Output() filterString: EventEmitter<string> = new EventEmitter<string>();

  constructor(private stateService: StateService) {
  }

  ngOnInit(): void {
    this.stateService.getValues().pipe(map(value => value.forEach(value1 => this.allValues.push(value1)))).subscribe();
  }

  onApplyFilter(): void {
    let stringBuilder = '';
    this.filterValues.forEach((value, index) => {
      if (value !== '') {
        stringBuilder = stringBuilder + this.filterKeys[index] + ':' + value + ',';
      }
    });
    this.filterString.emit(stringBuilder);
  }

  suggestionsForKey(filterKey: string, filterValue: string): string[] {
    const suggestions: string[] = [];

    this.allValues.filter(value => value.questionText.includes(filterKey)).forEach(value => suggestions.push(value.answerText));

    if (filterValue === '') {
      return [...new Set(suggestions)];
    } else {
      return [...new Set(suggestions)].filter(value => value.toLocaleLowerCase().includes(filterValue.toLocaleLowerCase()));
    }
  }
}
