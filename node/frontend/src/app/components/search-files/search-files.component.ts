import { Component, OnInit } from '@angular/core';
import {FssService} from "../../services/fss.service";

@Component({
  selector: 'app-search-files',
  templateUrl: './search-files.component.html',
  styleUrls: ['./search-files.component.scss']
})
export class SearchFilesComponent implements OnInit {

  constructor(private _HTTP : FssService) { }

  params: any[] = [];
  ngOnInit() {

  }

  doSearch(){

    var max = 10;

    //for(let i = 0; i< max;i++)
    this._HTTP.doSearch({name: "IMG_20191127_122140667"}).subscribe( res=>{
      console.log(res);
    });

  }


}
