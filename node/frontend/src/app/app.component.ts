import {Component, OnDestroy, OnInit} from '@angular/core';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import {VersionService} from "./services/version.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy{

  title = 'frontend';
  enlaces: any;
  brand: any;
  configuration: any;
  version: string;

  constructor(private _VS: VersionService){}

  ngOnInit(): void {
    this.enlaces = [];
    this.enlaces.push({ view: 'Subir archivos', url: '/uploads'});
    this.enlaces.push({ view: 'Compartidos', url: '/shareds'});
    this.enlaces.push({ view: 'Busqueda avanzada', url: '/searchs'});
    this.brand = {url: '/', logoUrl: './assets/logo.jpg', logo: 'SD&PP'};
    this.configuration= {component : ConfigurationComponent};
    this.version = "aaa";
  }

  handleEvent($event){
    console.log($event);
  }

  ngOnDestroy(): void {
  }
}
