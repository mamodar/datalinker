import {Component} from '@angular/core';
import {StateService} from '../../../services/state.service';
import {flatMap, take} from 'rxjs/operators';

/**
 * This component connects new resources with a selected project.
 * It persists new projects by calls to the backend.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-new-resource-attach',
  templateUrl: './new-resource-attach.component.html',
  styleUrls: ['./new-resource-attach.component.css']
})
export class NewResourceAttachComponent {

  constructor(public stateService: StateService) {
  }

  connect() {
    this.stateService.getNewShownResources().pipe(flatMap(_ => _)).subscribe(
      resource => this.stateService.createResource(resource).pipe(take(1)).subscribe(_ => {
        this.stateService.resetNewResources();
        this.stateService.getResources();
      })).unsubscribe();

  }

}
