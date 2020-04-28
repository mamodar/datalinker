import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {StateService} from './state.service';


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
    if (!this.stateService.getLoggedInUser().getValue()) {
      this.router.navigate(['/login']);
    }
    return true;
  }


}
