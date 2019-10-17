import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import {Observable, of} from 'rxjs';


@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor(){}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError((err, caught) => {
      if (err instanceof HttpErrorResponse) {
        let errorMessage = err.message;
        if(err.error.message)
          errorMessage = err.error.message;
        throw new Error(errorMessage);
      }
      return of(err);
    }) as any);
  }
}
