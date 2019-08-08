import { Component, OnInit } from '@angular/core';
import { ConfigurationComponent } from './components/configuration/configuration.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

  title = 'frontend';
  enlaces: any;
  brand: any;
  configuration: any;


  ngOnInit(): void {
    this.enlaces = [];
    this.enlaces.push({ view: 'Subir archivos', url: '/uploads'});
    this.enlaces.push({ view: 'Compartidos', url: '/shareds'});
    this.enlaces.push({ view: 'Busqueda avanzada', url: '/searchs'});
    this.brand = {url: '/', logoUrl: './assets/logo.jpg', logo: 'SD&PP'};
    this.configuration= {component : ConfigurationComponent};
  }

  handleEvent($event){
    console.log($event);
  }
}
