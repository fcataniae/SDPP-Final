import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import {ReduxService} from "../../redux/redux.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent implements OnInit {

  constructor(private state$: ReduxService,
              private dialog$: MatDialogRef<ConfigurationComponent>) { }

  configuration$: Observable<any> = this.state$.getSelector('config');

  ngOnInit() {
    this.state$.getConfiguration();
  }

  onClose(save: boolean, config: any){
    if(save)
      this.state$.saveConfiguration( config );
    this.dialog$.close();
  }

}
