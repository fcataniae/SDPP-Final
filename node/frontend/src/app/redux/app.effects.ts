import { Injectable } from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {VersionService} from "../services/version.service";
import {catchError, concatMap, map, switchMap} from "rxjs/operators";
import {Observable, of} from "rxjs";
import {errorVersion, getVersion, succesVersion} from "./app.actions";
import {Action, Store} from "@ngrx/store";

@Injectable({
  providedIn: 'root'
})
export class AppEffects {

  loadVersion$ =  createEffect( () =>this.actions$.pipe(
      ofType(getVersion),
      switchMap( action => this.version$.getVersion()
          .pipe(
             catchError(err => of(errorVersion({version: err}))),
             map( res => succesVersion({version: res}))
          )
        )
      ),
    {dispatch: true}
    );

  constructor(private actions$: Actions, private version$: VersionService, private store$: Store<any>) {}
}
