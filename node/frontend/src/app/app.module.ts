import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { APP_BASE_HREF, LocationStrategy, HashLocationStrategy } from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgxFileDropModule } from 'ngx-file-drop';
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
import { menuReducer } from "./redux/app.reducers";


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
    ConfirmacionPopupComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    NgxFileDropModule,
    StoreModule.forRoot(
      { menu : menuReducer }, { metaReducers }
    ),
    EffectsModule.forRoot([AppEffects])
  ],
  entryComponents: [ ConfigurationComponent, DetailComponent, ConfirmacionPopupComponent],
  exports: [ ConfigurationComponent ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    },
    {
           provide: APP_BASE_HREF,
           useValue: '/' + (window.location.pathname.split('/')[1] || 'desa')
   },
   { provide: LocationStrategy, useClass: HashLocationStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
