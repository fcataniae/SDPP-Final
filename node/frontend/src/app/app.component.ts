import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {ReduxService} from "./redux/redux.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  config$: Observable<any> = this.state$.getSelector('menu');

  constructor(private state$: ReduxService) {
  }

  ngOnInit() {
    this.state$.getVersion()
  }

  handleEvent($event) {
    console.log($event);
  }
}
