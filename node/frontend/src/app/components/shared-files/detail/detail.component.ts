import { Component, OnInit, Input } from '@angular/core';

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

  ngOnInit() {
  }

}
