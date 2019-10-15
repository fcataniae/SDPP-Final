import {Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver, OnDestroy} from '@angular/core';
import { FssService } from 'src/app/services/fss.service';
import { DetailComponent } from './detail/detail.component';
import {ReduxService} from "../../redux/redux.service";
import {map, takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";

const _FILE = 'file';

@Component({
  selector: 'app-shared-files',
  templateUrl: './shared-files.component.html',
  styleUrls: ['./shared-files.component.scss']
})
export class SharedFilesComponent implements OnInit, OnDestroy {

  constructor(protected  state$ : ReduxService, private resolver$ : ComponentFactoryResolver) { }
  @ViewChild('dirs', { static:true, read: ViewContainerRef }) entry: ViewContainerRef;
  sharedfiles = this.state$.getSelector('file');

  destroy$ = new Subject();

  ngOnInit() {
    this.state$.getAllFiles();
  }

  ngOnDestroy(){
    this.destroy$.next();
    this.destroy$.complete();
  }
  load(){
    const factory = this.resolver$.resolveComponentFactory(DetailComponent);
    this.entry.clear();
    this.sharedfiles.forEach( f => {
      console.log(f);
      var di = this.entry.createComponent(factory);
      di.instance.dir = false;
      di.instance.name = f.name;
      di.instance.file = true;
    });
  }

}
