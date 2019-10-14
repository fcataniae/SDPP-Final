import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'my-navbar',
  templateUrl: './navbar-custom.component.html',
  styleUrls: ['./navbar-custom.component.scss']
})
export class NavbarCustomComponent implements OnInit {

  @Input() enlaces: any;
  @Input() brand: any;
  @Input() configuration: any;
  @Input() searchbox: any;
  @Output() event = new EventEmitter<any>();

  searchText: String;


  constructor(private _DIALOG: MatDialog) { }


  ngOnInit() {
  }

  onEvent($event){
    this.event.emit(this.searchText);
  }
  openDialog(){
    var dialog = this._DIALOG.open( this.configuration.component);
    dialog.afterClosed().subscribe();
  }

}
