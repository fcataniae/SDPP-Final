import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'custom-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  constructor() { }

  @Input() file: any;
  @Input() name: any;
  @Input() dir: any;
  @Output() event = new EventEmitter<any>();

  ngOnInit() {
  }

  onEvent($event){
    this.event.emit($event);
  }

}
