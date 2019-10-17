import {Injectable} from "@angular/core";
import {select, Store} from "@ngrx/store";
import {getAllFiles, getConfiguration, getVersion, removeFiles, saveConfiguration} from "./app.actions";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ReduxService{

  constructor(protected store$: Store<any>){}

  public getVersion(){
    this.store$.dispatch(getVersion());
  }

  public getAllFiles(){
    this.store$.dispatch(getAllFiles());
  }

  public getSelector(prop: string) : Observable<any>{
    return this.store$.pipe(select(prop));
  }


  deleteFiles() {
    this.store$.dispatch(removeFiles());
  }

  getConfiguration() {
    this.store$.dispatch(getConfiguration());
  }

  saveConfiguration(config: any) {
    this.store$.dispatch(saveConfiguration({config: config}))
  }
}
