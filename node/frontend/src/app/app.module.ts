

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { APP_BASE_HREF, LocationStrategy } from '@angular/common';
import { CustomLocationStrategy } from './app.common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
@NgModule({
  declarations: [
    AppComponent,
    ConfigurationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
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
