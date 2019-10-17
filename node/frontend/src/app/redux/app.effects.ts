import { Injectable } from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {VersionService} from "../services/version.service";
import {catchError, map, switchMap, tap} from "rxjs/operators";
import {
  errorGetAllFiles, errorGetConfiguration,
  errorGetVersion, errorSaveConfiguration,
  getAllFiles, getConfiguration,
  getVersion, saveConfiguration,
  successGetAllFiles, successGetConfiguration,
  successGetVersion, successSaveConfiguration
} from "./app.actions";
import {of} from "rxjs";
import {FssService} from "../services/fss.service";
import {ConfigurationService} from "../services/configuration.service";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class AppEffects {

  loadVersion$ =  createEffect( () =>this.actions$.pipe(
      ofType(getVersion),
      switchMap( () => this.version$.getVersion()
          .pipe(
             map( result => successGetVersion({version: result.version})),
             catchError(err => of(errorGetVersion({error: err})))
          )
        )
      ),
    {dispatch: true}
    );

  loadFiles$ = createEffect(() => this.actions$.pipe(
    ofType(getAllFiles),
    switchMap(() => this.fs$.getSharedList()
        .pipe(
          map(res => successGetAllFiles({files: Array.from(res)})),
          catchError(err => of(errorGetAllFiles({error: err})))
        )
      )
    ),
    {dispatch: true}
  );

  loadConfig$ = createEffect( () => this.actions$.pipe(
      ofType(getConfiguration),
      switchMap(() => this.config$.getConfigurations()
        .pipe(
          map(res => successGetConfiguration({config: res})),
          catchError(err => of(errorGetConfiguration({error: err})))
        )
      )
    )
  );

  saveConfig$ = createEffect( () => this.actions$.pipe(
      ofType(saveConfiguration),
      switchMap( action => this.config$.saveConfiguration( action.config)
        .pipe(
          map(res => successSaveConfiguration({config: action.config})),
          catchError(err => of(errorSaveConfiguration({error: err})))
        )
      )
    )
  );

  onError$ = createEffect( () => this.actions$.pipe(
      ofType(errorSaveConfiguration, errorGetConfiguration, errorGetAllFiles, errorGetVersion),
      tap(console.log),
      tap( action => this.notifier$.error( 'Ocurrio un error: '.concat(action.error)))
    )
    ,{dispatch: false}
  );

  successSaveConfig$ = createEffect( () => this.actions$.pipe(
      ofType(successSaveConfiguration),
      tap( () => this.notifier$.success( 'Se guardo correctamente la configuracion!'))
    )
    ,{dispatch: false}
  );

  constructor(private actions$: Actions,
              private version$: VersionService,
              private config$: ConfigurationService,
              private fs$: FssService,
              private notifier$: ToastrService) {}
}
