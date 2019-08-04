import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { APP_BASE_HREF, LocationStrategy } from '@angular/common';
import { CustomLocationStrategy } from './app.common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { MatInputModule, MatIconModule, MatDialogModule  } from '@angular/material';
import { NavbarCustomComponent } from './components/navbar-custom/navbar-custom.component';

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationComponent,
    NavbarCustomComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule
  ],
  entryComponents: [ ConfigurationComponent ],
  exports: [ ConfigurationComponent ],
  providers: [
    {
           provide: APP_BASE_HREF,
           useValue: '/' + (window.location.pathname.split('/')[1] || 'desa')
   },
   { provide: LocationStrategy, useClass: CustomLocationStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
