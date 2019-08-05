import { Component, OnInit } from '@angular/core';
import { FssService } from 'src/app/services/fss.service';

@Component({
  selector: 'app-shared-files',
  templateUrl: './shared-files.component.html',
  styleUrls: ['./shared-files.component.scss']
})
export class SharedFilesComponent implements OnInit {

  constructor(private _FSS: FssService) { }

  ngOnInit() {
    this._FSS.getSharedList().subscribe(
      res => console.log(res)
    );
  }

}
