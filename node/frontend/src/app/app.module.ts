import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { APP_BASE_HREF, LocationStrategy, HashLocationStrategy, PathLocationStrategy } from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatInputModule, MatIconModule, MatDialogModule  } from '@angular/material';
import { NavbarCustomComponent } from './components/navbar-custom/navbar-custom.component';
import { UploadFilesComponent } from './components/upload-files/upload-files.component';
import { SharedFilesComponent } from './components/shared-files/shared-files.component';
import { DetailComponent } from './components/shared-files/detail/detail.component';
import { SearchFilesComponent } from './components/search-files/search-files.component';
import { CustomUploadComponent } from './components/upload-files/custom-upload/custom-upload.component';
import {ConfirmacionPopupComponent} from "./components/popup/confirmacion-popup.component";
import {Interceptor} from "./services/interceptor.service";
import { EffectsModule } from '@ngrx/effects';
import { AppEffects } from './redux/app.effects';
import {ActionReducer, MetaReducer, StoreModule} from '@ngrx/store';
import {configReducer, fileReducer, menuReducer} from "./redux/app.reducers";
import { LoadingComponent } from './components/loading/loading.component';
import {ToastrModule} from "ngx-toastr";
import {configs} from "./toast.config";
import { EmbedDialogComponent } from './components/embed-dialog/embed-dialog.component';
import { UrlPipe } from './url-pipe.pipe';


export function debug(reducer: ActionReducer<any>): ActionReducer<any> {
  return function(state, action) {
    console.log('state', state);
    console.log('action', action);

    return reducer(state, action);
  };
}

export const metaReducers: MetaReducer<any>[] = [debug];


@NgModule({
  declarations: [
    AppComponent,
    ConfigurationComponent,
    NavbarCustomComponent,
    UploadFilesComponent,
    SharedFilesComponent,
    DetailComponent,
    SearchFilesComponent,
    CustomUploadComponent,
    ConfirmacionPopupComponent,
    LoadingComponent,
    EmbedDialogComponent,
    UrlPipe
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    StoreModule.forRoot(
      { menu : menuReducer, file: fileReducer, config: configReducer },
      { metaReducers }
    ),
    EffectsModule.forRoot([AppEffects]),
    ToastrModule.forRoot(configs)
  ],
  entryComponents: [ ConfigurationComponent, DetailComponent, ConfirmacionPopupComponent, EmbedDialogComponent],
  exports: [ ConfigurationComponent ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    },
    {
           provide: APP_BASE_HREF,
           useValue: '/'
   },
   { provide: LocationStrategy, useClass: PathLocationStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
