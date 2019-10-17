import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver, OnDestroy} from '@angular/core';
import { DetailComponent } from './detail/detail.component';
import { ReduxService} from "../../redux/redux.service";
import { Subject} from "rxjs";
import { debounceTime, map, takeUntil} from "rxjs/operators";


@Component({
  selector: 'app-shared-files',
  templateUrl: './shared-files.component.html',
  styleUrls: ['./shared-files.component.scss']
})
export class SharedFilesComponent implements OnInit, OnDestroy {

  constructor(protected  state$ : ReduxService, private resolver$ : ComponentFactoryResolver) { }
  @ViewChild('dirs', { static:true, read: ViewContainerRef }) entry: ViewContainerRef;
  sharedfiles$ = this.state$.getSelector('file');
  sharedfiles = [];
  destroy$ = new Subject();
  loading = false;

  ngOnInit() {
    this.state$.getAllFiles();
    this.sharedfiles$
      .pipe(
        debounceTime(300),
        map(res => res.files),
        takeUntil(this.destroy$)
      )
      .subscribe(res => {
        this.sharedfiles = res;
        this.load();
        this.loading = true;
      });
  }

  ngOnDestroy(){
    this.state$.deleteFiles();
    this.destroy$.next();
    this.destroy$.complete();
  }

  load(){
    const factory = this.resolver$.resolveComponentFactory(DetailComponent);
    this.entry.clear();
    this.sharedfiles.forEach( f => {
      var di = this.entry.createComponent(factory);
      di.instance.dir = false;
      di.instance.name = f.name;
      di.instance.file = true;
    });
  }

}
