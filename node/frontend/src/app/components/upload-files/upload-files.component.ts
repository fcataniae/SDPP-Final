import { Component, OnInit } from '@angular/core';
import { FssService } from 'src/app/services/fss.service';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
  styleUrls: ['./upload-files.component.scss']
})
export class UploadFilesComponent implements OnInit {

  constructor(private _FSS: FssService) { }
  url: string;
  ngOnInit() {
    this.url = this._FSS.getUploadUrl();
  }
}
