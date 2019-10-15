import { Injectable } from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {VersionService} from "../services/version.service";
import {catchError, map, switchMap} from "rxjs/operators";
import {
  errorGetAllFiles,
  errorGetVersion,
  getAllFiles,
  getVersion,
  successGetAllFiles,
  successGetVersion
} from "./app.actions";
import { Store} from "@ngrx/store";
import {of} from "rxjs";
import {FssService} from "../services/fss.service";

@Injectable({
  providedIn: 'root'
})
export class AppEffects {

  loadVersion$ =  createEffect( () =>this.actions$.pipe(
      ofType(getVersion),
      switchMap( () => this.version$.getVersion()
          .pipe(
             catchError(err => of(errorGetVersion({error: err}))),
             map( result => successGetVersion({version: result.version}))
          )
        )
      ),
    {dispatch: true}
    );

  loadFiles$ = createEffect(() => this.actions$.pipe(
    ofType(getAllFiles),
    switchMap(() => this.fs$.getSharedList()
        .pipe(
          catchError(err => of(errorGetAllFiles({error: err}))),
          map(res => successGetAllFiles({files: res}))
        )
      )
    ),
    {dispatch: true}
  );
  constructor(private actions$: Actions,
              private version$: VersionService,
              private fs$: FssService) {}
}
