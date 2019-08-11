import { Component, OnInit } from '@angular/core';
import { FileSystemDirectoryEntry, NgxFileDropEntry, FileSystemFileEntry } from 'ngx-file-drop';
import { FssService } from 'src/app/services/fss.service';
import { HttpEventType } from '@angular/common/http';

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
