import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver  } from '@angular/core';
import { FssService } from 'src/app/services/fss.service';
import { NgElement, WithProperties } from '@angular/elements';
import { DetailComponent } from './detail/detail.component';

@Component({
  selector: 'app-shared-files',
  templateUrl: './shared-files.component.html',
  styleUrls: ['./shared-files.component.scss']
})
export class SharedFilesComponent implements OnInit {

  constructor(private _FSS: FssService, private _RESOLVER: ComponentFactoryResolver) { }
  @ViewChild('dirs', { static:true, read: ViewContainerRef }) entry: ViewContainerRef;
  sharedfiles: any;
  ngOnInit() {
    this._FSS.getSharedList().subscribe(
      res => {
        this.sharedfiles = res;
        this.load();
      }
    );
  }

  load(){
    const factory = this._RESOLVER.resolveComponentFactory(DetailComponent);
    this.sharedfiles.dirs.forEach( d => {

    });
    this.sharedfiles.files.forEach( f   => {
      //var fi = document.createElement('custom-detail') as NgElement & WithProperties<{file:any,dir:any,name:any}>;
      var fi = this.entry.createComponent(factory);
      console.log(fi);
      fi.instance.name = f;
      fi.instance.file = true;
    });
  }
}
