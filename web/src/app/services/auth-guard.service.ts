import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {StateService} from './state.service';
import {LoginComponent} from '../login/login.component';


@Injectable({
  providedIn: 'root'
})

export class AuthGuardService implements CanActivate {
  constructor(
    private router: Router,
    private stateService: StateService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.stateService.getLoggedInUser().getValue() === null) {
      this.router.navigate(['/login'], {queryParams: route.queryParams} );
      return false;
    }
    if (!this.stateService.getLoggedInUser().getValue().userName) {
      this.router.navigate(['/login'], {queryParams: route.queryParams} );
      return false;
    }
    return true;
  }


}
