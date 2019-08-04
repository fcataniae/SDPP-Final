import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { APP_BASE_HREF, LocationStrategy } from '@angular/common';
import { CustomLocationStrategy } from './app.common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { MatInputModule } from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatInputModule
  ],
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
