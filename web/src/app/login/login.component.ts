import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {StateService} from '../services/state.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  login: string;
  password: string;
  // no password and no username should not show the error message
  public passwordCorrect = true;
  constructor(public stateService: StateService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {

  }

  loginClick(): void {
    console.log(this.passwordCorrect );
    this.passwordCorrect = false;
    this.stateService.loginUser(this.login, this.password).subscribe(_ => {
      if ((this.stateService.getLoggedInUser().getValue() !== null) && (this.stateService.getLoggedInUser().getValue().userName !== null)) {
        this.router.navigate(['/projects'], {queryParams: this.route.snapshot.queryParams});
        this.passwordCorrect = true;
      }
    });

  }

  logoutClick(): void {
    this.stateService.logoutUser().subscribe(_ => {
      this.router.navigate(['/login'], {queryParams: this.route.snapshot.queryParams});
    });

  }
}
