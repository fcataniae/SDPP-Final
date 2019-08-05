import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { APP_BASE_HREF, LocationStrategy, HashLocationStrategy } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgxFileDropModule } from 'ngx-file-drop';
import { MatInputModule, MatIconModule, MatDialogModule  } from '@angular/material';
import { NavbarCustomComponent } from './components/navbar-custom/navbar-custom.component';
import { UploadFilesComponent } from './components/upload-files/upload-files.component';
import { SharedFilesComponent } from './components/shared-files/shared-files.component';
import { DetailComponent } from './components/shared-files/detail/detail.component';

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationComponent,
    NavbarCustomComponent,
    UploadFilesComponent,
    SharedFilesComponent,
    DetailComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    NgxFileDropModule
  ],
  entryComponents: [ ConfigurationComponent, DetailComponent],
  exports: [ ConfigurationComponent ],
  providers: [
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
