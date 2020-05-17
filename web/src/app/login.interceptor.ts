import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor} from '@angular/common/http';
import {Observable} from 'rxjs';

/**
 * This interceptor adds the current users' username and password to each http request.
 * @author Kyanoush Yahosseini
 */

@Injectable()
export class LoginInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const currentUser = sessionStorage.getItem('currentUser');
    const currentPassword = sessionStorage.getItem('currentPassword');
    if (currentUser  && currentPassword) {
      return next.handle(request.clone(
        {headers: request.headers.
          set('Content-Type', 'application/json').
          set('Authorization', 'Basic ' + btoa(currentUser + ':' + currentPassword) )}));
    } else {
      return next.handle(request);
    }

  }
}
