import {Component} from '@angular/core';
import {StateService} from './services/state.service';

/**
 * This component is the application root. It contains the header and footer.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {


  constructor(public stateService: StateService) {
  }
}
