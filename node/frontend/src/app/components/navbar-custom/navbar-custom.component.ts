import { Component, OnInit, Input } from '@angular/core';
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

  constructor(private _DIALOG: MatDialog) { }

  ngOnInit() {
  }

  openDialog(){
    console.log(this.configuration.component);
    var dialog = this._DIALOG.open( this.configuration.component);
    dialog.afterClosed().subscribe();
  }

}
