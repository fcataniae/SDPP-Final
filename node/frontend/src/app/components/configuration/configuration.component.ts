import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from './configuration.service';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent implements OnInit {

  constructor(private _HTTP: ConfigurationService,
              private _DIALOG: MatDialogRef<ConfigurationComponent>) { }

  configuration: any;

  ngOnInit() {
    this._HTTP.getConfigurations().subscribe(
      res => {
        this.configuration = res
        console.log(this.configuration);
      }
    );
  }

  onClose(){
    this._DIALOG.close();
  }
  
}
