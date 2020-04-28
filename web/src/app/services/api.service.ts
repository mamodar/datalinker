import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError} from 'rxjs/operators';
import {AuthUser} from '../models/authUser';

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
    let httpOptions = {};
    if (user) {
      httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: 'Basic ' + btoa(user.userName + ':' +     user.password)
        })
      };
    }
    console.warn(Date() + ' GET:' + path);
    return this.http.get(environment.appUrl + path, httpOptions)
    .pipe(catchError(ApiService.formatErrors));
  }

  put(path: string, body: any = {}): Observable<any> {
    console.warn(Date() + ' PUT:' + path + 'body:' + JSON.stringify(body));
    return this.http.put(
      environment.appUrl + path,
      body
    ).pipe(catchError(ApiService.formatErrors));
  }

  post(path: string, body: any = {}): Observable<any> {
    console.warn(Date() + ' POST:' + path + 'body:' + JSON.stringify(body));
    return this.http.post(
      environment.appUrl + path,
      body
    ).pipe(catchError(ApiService.formatErrors));
  }

  delete(path): Observable<any> {
    console.warn(Date() + ' DELETE:' + path);
    return this.http.delete(
      environment.appUrl + path
    ).pipe(catchError(ApiService.formatErrors));
  }
}
