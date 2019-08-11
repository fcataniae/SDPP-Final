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

  ngOnInit() {
  }
  // public files: NgxFileDropEntry[] = [];
  //
  // public dropped(files: NgxFileDropEntry[]) {
  //   this.files = files;
  //   for (const droppedFile of files) {
  //
  //     // Is it a file?
  //     if (droppedFile.fileEntry.isFile) {
  //       const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
  //       fileEntry.file((file: File) => {
  //
  //         // Here you can access the real file
  //         console.log(droppedFile.relativePath, file);
  //
  //         /**
  //         // You could upload it like this:
  //         const formData = new FormData()
  //         formData.append('logo', file, relativePath)
  //
  //         // Headers
  //         const headers = new HttpHeaders({
  //           'security-token': 'mytoken'
  //         })
  //
  //         this.http.post('https://mybackend.com/api/upload/sanitize-and-save-logo', formData, { headers: headers, responseType: 'blob' })
  //         .subscribe(data => {
  //           // Sanitized logo returned from backend
  //         })
  //         **/
  //
  //       });
  //     } else {
  //       // It was a directory (empty directories are added, otherwise only files)
  //       const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
  //       console.log(droppedFile.relativePath, fileEntry);
  //     }
  //   }
  // }
  //
  // public fileOver(event){
  //   console.log(event);
  // }
  //
  // public fileLeave(event){
  //   console.log(event);
  // }

  files: any[];

  handleFileInput(fs: FileList) {
    this.files = []
    for(let i = 0; i< fs.length ; i++){
      this.files.push({name: fs.item(i).name, progress: 0});
      this._FSS.postFile(fs.item(i)).subscribe(
        event => {
          console.log(event);
          if ( event.type === HttpEventType.UploadProgress ) {
            this.files[i].progress =  Math.round((100 * event.loaded) / event.total);
          }
        }
      );

    }

  }
}
