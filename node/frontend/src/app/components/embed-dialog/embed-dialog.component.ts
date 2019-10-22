import {Component, Inject, Input, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {HttpClient} from "@angular/common/http";

export interface Data {
  src: string;
  name: string
}
@Component({
  selector: 'app-embed-dialog',
  templateUrl: './embed-dialog.component.html',
  styleUrls: ['./embed-dialog.component.scss']
})
export class EmbedDialogComponent implements OnInit {

  src: string;
  show = false;
  name: string;

  constructor(private http$: HttpClient,
              private dialog$: MatDialogRef<EmbedDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data$: Data)
  {
    this.name = data$.name;
    this.src = data$.src;
    this.show = true;
  }

  ngOnInit() {
  }

  onClose(){
    this.dialog$.close();
  }

  downloadItem(){
    console.log("download");
    this.http$.get(this.src,{ responseType: 'blob' })
      .subscribe(data =>
        {
          var downloadURL = URL.createObjectURL(data);
          var link = document.createElement('a');
          link.href = downloadURL;
          link.download = this.name;
          link.click();
          this.onClose();
        });
  }
}
