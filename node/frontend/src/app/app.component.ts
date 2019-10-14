import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {select, Store} from "@ngrx/store";
import {getVersion} from "./redux/app.actions";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  configs: Observable<any> = this.store$.pipe(select('menu'));

  constructor(private store$: Store<{ state: any}>) {
  }

  ngOnInit() {
    this.store$.dispatch(getVersion());
  }

  handleEvent($event) {
    console.log($event);
  }
}
