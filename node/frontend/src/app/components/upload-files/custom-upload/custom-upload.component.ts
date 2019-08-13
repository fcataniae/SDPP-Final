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
    this.initDropArea();
  }


  handleDrop($event) {
    let dt = $event.dataTransfer;
    let files = dt.files;

    console.log(files);
    this.handleFileInput(files);
  }

  static preventDefaults ($event) {
    $event.preventDefault();
    $event.stopPropagation();
  }

  files: any[];
  @Input() uploadUrl: string;

  handleFileInput(fs: FileList) {
    console.log('2 ' + fs );
      this.clearFinished();
      console.log(fs.length);
      for (let i = 0; i < fs.length; i++) {
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

  private initDropArea() {
    let droparea = document.querySelector('.dropeable');
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
      droparea.addEventListener(eventName, CustomUploadComponent.preventDefaults, false)
    });

    let highlight = function ($event) {
      this.classList.add('highlight');
    };

    let unhighlight = function ($event) {
      this.classList.remove('highlight');
    };

    ['dragenter', 'dragover'].forEach(eventName => {
      droparea.addEventListener(eventName, highlight, false);
    });

    ['dragleave', 'drop'].forEach(eventName => {
      droparea.addEventListener(eventName, unhighlight, false);
    });

  }
}
