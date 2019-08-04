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
    this.enlaces.push({ view: 'upload', url: '/upload/files'});
    this.brand = {url: '/', logoUrl: './assets/logo.jpg', logo: 'Logo'};
    this.configuration= {component : ConfigurationComponent};
  }


}
