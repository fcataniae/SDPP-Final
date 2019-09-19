import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import {Observable, of, throwError} from 'rxjs';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import {ConfirmacionPopupComponent} from "../components/popup/confirmacion-popup.component";


@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor(private _matdialog: MatDialog, private _router : Router){}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError((err, caught) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 500) {
          console.log(err);
          console.log(caught);
          let dialog = this._matdialog.open(ConfirmacionPopupComponent,{
            data: {mensaje:"Ocurrio un error inesperado: " + err.error.message, titulo: "Error:",error: true}
          });
          dialog.afterClosed().subscribe();
        }else if (err.status === 404){
          console.log(err);
          console.log(caught);
          let dialog = this._matdialog.open(ConfirmacionPopupComponent,{
            data: {mensaje:"No se pudo encontrar el recurso especificado " + err.error.message, titulo: "Error:",error: true}
          });
          dialog.afterClosed().subscribe();
        }
      }
      return of(err);
    }) as any);
  }
}
