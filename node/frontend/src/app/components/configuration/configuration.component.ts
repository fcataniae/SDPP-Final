import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from './configuration.service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent implements OnInit {

  constructor(private _HTTP: ConfigurationService) { }

  configuration: any;

  ngOnInit() {
    this._HTTP.getConfigurations().subscribe(
      res => {
        this.configuration = res
        console.log(this.configuration);
      }
    );
  }

}
