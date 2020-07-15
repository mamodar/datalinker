import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {HttpClient, HttpHeaders, HttpRequest} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError} from 'rxjs/operators';
import {AuthUser} from '../models/authUser';

/**
 * This service allows low-level API calls to a REST interface.
 * This service should not be called directly from components but is used by {@link ProjectService} and {@link ResourceService}.
 * @author Kyanoush Yahosseini
 */
@Injectable({
  providedIn: 'root'
})


export class ApiService {
  constructor(private http: HttpClient) {
  }

  private static formatErrors(error: any) {
    return throwError(error);
  }

  get(path: string, user?: AuthUser): Observable<any> {
    console.log(Date() + ' GET:' + path);
    let httpOptions = {};
    if (user) {
      httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: 'Basic ' + btoa(user.userName + ':' + user.password)
        })
      };
      return this.http.get(environment.appUrl + path, httpOptions);
    }
    return this.http.get(environment.appUrl + path, httpOptions)
    .pipe(catchError(ApiService.formatErrors));
  }

  put(path: string, body: any = {}): Observable<any> {
    console.log(Date() + ' PUT:' + path + 'body:' + JSON.stringify(body));
    return this.http.put(
      environment.appUrl + path,
      body
    ).pipe(catchError(ApiService.formatErrors));
  }

  post(path: string, body: any = {}): Observable<any> {
    console.log(Date() + ' POST:' + path + 'body:' + JSON.stringify(body));

    return this.http.post(
      environment.appUrl + path,
      body).pipe(catchError(ApiService.formatErrors));
  }

  postWithProgress(path: string, body: any = {}): Observable<any> {
    console.log(Date() + ' POST:' + path + 'body:' + JSON.stringify(body));
    const req = new HttpRequest('POST', environment.appUrl + path, body, {
      reportProgress: true
    });
    return this.http.request(req).pipe(catchError(ApiService.formatErrors)
    );
  }

  delete(path): Observable<any> {
    console.log(Date() + ' DELETE:' + path);
    return this.http.delete(
      environment.appUrl + path
    ).pipe(catchError(ApiService.formatErrors));
  }
}
