import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StateService} from './services/state.service';
import {AuthUser} from './models/authUser';

@Injectable()
export class LoginInterceptor implements HttpInterceptor {

  constructor(private stateService: StateService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const authUser: AuthUser = this.stateService.getLoggedInUser().getValue();

    if (authUser) {
      return next.handle(request.clone(
        {headers: request.headers.
          set('Content-Type', 'application/json').
          set('Authorization', 'Basic ' + btoa(authUser.userName + ':' + authUser.password))}));
    } else {
      return next.handle(request);
    }

  }
}
