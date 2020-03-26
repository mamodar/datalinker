import { Injectable } from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  private formatErrors(error: any) {
    return  throwError(error);
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {

    console.warn(Date() + ' GET:' + path );
    return this.http.get(environment.appUrl + path, { params })
    .pipe(catchError(this.formatErrors));
  }

  put(path: string, body: any = {}): Observable<any> {
    console.warn(Date() + ' PUT:' + path + 'body:' +  JSON.stringify(body) );
    return this.http.put(
      environment.appUrl + path,
      body
    ).pipe(catchError(this.formatErrors));
  }

  post(path: string, body: any = {}): Observable<any> {
    console.warn(Date() + ' POST:' + path + 'body:' + JSON.stringify(body) );
    return this.http.post(
      environment.appUrl + path,
      body
    ).pipe(catchError(this.formatErrors));
  }

  delete(path): Observable<any> {
    console.warn(Date() + ' DELETE:' + path );
    return this.http.delete(
      environment.appUrl + path
    ).pipe(catchError(this.formatErrors));
  }
}
