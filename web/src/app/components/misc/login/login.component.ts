import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {StateService} from '../../../services/state.service';
import {catchError} from 'rxjs/operators';
import {of} from 'rxjs';

/**
 * The login and logout logic of the application.
 * @author Kyanoush Yahosseini
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  login: string;
  password: string;
  // no password and no username should not show the error message
  public passwordCorrect = true;
  public showSpinner = false;

  constructor(public stateService: StateService, private router: Router, private route: ActivatedRoute) {
  }


  loginClick(): void {
    this.showSpinner = true;
    this.stateService.loginUser(this.login, this.password).
    pipe(catchError(err => {
      this.passwordCorrect = false;
      return of(err);
    })).
    subscribe(_ => {
      this.showSpinner = false;
      if ((this.stateService.getLoggedInUser().getValue() !== null) && (this.stateService.getLoggedInUser().getValue().userName !== null)) {
        this.router.navigate(['/search'], {queryParams: this.route.snapshot.queryParams});
      }
    });

  }

  logoutClick(): void {
    this.stateService.logoutUser().subscribe(_ => {
      this.router.navigate(['/login'], {queryParams: this.route.snapshot.queryParams});
    });

  }
}
