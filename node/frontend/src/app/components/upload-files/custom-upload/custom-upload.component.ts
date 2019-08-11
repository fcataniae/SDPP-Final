import {Component, Input, OnInit} from '@angular/core';
import {HttpClient, HttpEventType} from "@angular/common/http";

@Component({
  selector: 'custom-upload',
  templateUrl: './custom-upload.component.html',
  styleUrls: ['./custom-upload.component.scss']
})
export class CustomUploadComponent implements OnInit {

  constructor(private _HTTP: HttpClient) {
  }

  ngOnInit() {
  }

  files: any[];
  @Input() uploadUrl: string;

  handleFileInput(fs: FileList) {
    this.clearFinished();
    for(let i = 0; i< fs.length ; i++){
      let file = {name: fs.item(i).name, progress: 0};
      this.files.push(file);
      this.postFile(fs.item(i), file)
    }
  }

  postFile(file: any, rep: any){
    const data = new FormData();
    data.append('file', file,file.name);
    this._HTTP.post(this.uploadUrl,data,{
      reportProgress: true,
      observe: 'events'
    }).subscribe(
      event => {
        if ( event.type === HttpEventType.UploadProgress ) {
          rep.progress =  Math.round((100 * event.loaded) / event.total);
        }
      }
    );
  }

  clearFinished(){
    if(this.files) {
      this.files.forEach(f => {
        if (f.progress == 100)
          this.files = this.files.filter(f2 => JSON.stringify(f2) !== JSON.stringify(f));
      });
    }else{
      this.files = [];
    }
  }
}
