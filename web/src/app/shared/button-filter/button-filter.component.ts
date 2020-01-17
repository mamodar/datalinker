import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-button-filter',
  templateUrl: './button-filter.component.html',
  styleUrls: ['./button-filter.component.css']
})
export class ButtonFilterComponent implements OnInit {

  constructor() { }
  @Output() checked = new EventEmitter<boolean>();
  ngOnInit() {
  }

  filter($event): void {
    this.checked.emit($event.target.checked);
  }
}
