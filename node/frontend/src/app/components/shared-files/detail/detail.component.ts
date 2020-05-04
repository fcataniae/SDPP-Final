import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import {MatDialog} from "@angular/material";
import {EmbedDialogComponent} from "../../embed-dialog/embed-dialog.component";

@Component({
  selector: 'custom-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  constructor(private dialog$: MatDialog) { }

  @Input() file: any;
  @Input() name: any;
  @Input() item: any;
  @Input() dir: any;

  ngOnInit() {
  }

  onEvent($event){
    $event.preventDefault();
    var dialog = this.dialog$.open(EmbedDialogComponent, {
      width: '90%',
      height: '90%',
      data: {src : this.item.link, name: this.name}
    });
    dialog.afterClosed().subscribe();
  }

}
