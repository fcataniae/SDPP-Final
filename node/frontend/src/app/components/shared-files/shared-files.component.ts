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
  sharedfiles: any;
  sharedfilesselected: any;
  ngOnInit() {
    this._FSS.getSharedList().subscribe(
      res => {
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
    this.sharedfilesselected.dirs.forEach( d => {
      var di = this.entry.createComponent(factory);
      di.instance.dir = true;
      di.instance.name = d.dirname.replace('/','\\').split('\\')[d.dirname.replace('/','\\').split('\\').length - 1];
      di.instance.event.subscribe(
        r => {
          this.onEvent(r);
        }
      )
    });
    this.sharedfilesselected.files.forEach( f   => {
      var fi = this.entry.createComponent(factory);
      console.log(fi);
      fi.instance.name = f;
      fi.instance.file = true;
      fi.instance.event.subscribe(
        r => {
          this.onEvent(r);
        }
      )
    });
  }

  onEvent($event){
    let selected = $event.target.dataset.name;
    let file = $event.target.classList.value.includes(_FILE);
    this.doSearch(selected, file);
  }

  doSearch(filename: string, isFile: boolean){
    if(isFile){
      this._FSS.getBinaryFile(btoa(this.sharedfilesselected.dirname + filename)).subscribe(
        res => {
          console.log(res);
        }
      );

    }else{
      this.sharedfilesselected.dirs.forEach( d => {
        if(d.dirname.includes(filename)){
          this.sharedfilesselected = d;
          console.log(this.sharedfilesselected);
          this.load();
        }
      } );
    }
  }
}
