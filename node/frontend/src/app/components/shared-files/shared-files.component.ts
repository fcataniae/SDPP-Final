import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver  } from '@angular/core';
import { FssService } from 'src/app/services/fss.service';
import { DetailComponent } from './detail/detail.component';

const _FILE = 'file';

@Component({
  selector: 'app-shared-files',
  templateUrl: './shared-files.component.html',
  styleUrls: ['./shared-files.component.scss']
})
export class SharedFilesComponent implements OnInit {

  constructor(private _FSS: FssService, private _RESOLVER: ComponentFactoryResolver) { }
  @ViewChild('dirs', { static:true, read: ViewContainerRef }) entry: ViewContainerRef;
  sharedfiles = [];
  sharedfilesselected = [];
  ngOnInit() {
    this._FSS.getSharedList().subscribe(
      (res : any[]) => {
        this.sharedfiles = res;
        this.sharedfilesselected = res;
        this.load();
      }
    );
  }

  load(){

    const factory = this._RESOLVER.resolveComponentFactory(DetailComponent);
    this.entry.clear();
    console.log(this.sharedfilesselected);
    this.sharedfilesselected.forEach( f => {
      var di = this.entry.createComponent(factory);
      di.instance.dir = false;
      di.instance.name = f.name;
      di.instance.file = true;
    });
  }

}
