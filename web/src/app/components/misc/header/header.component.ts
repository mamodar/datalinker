import {Component} from '@angular/core';
import {StateService} from '../../../services/state.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(public stateService: StateService) {
  }
}
