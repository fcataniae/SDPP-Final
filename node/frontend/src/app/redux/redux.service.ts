import {Injectable} from "@angular/core";
import {select, Store} from "@ngrx/store";
import {getAllFiles, getVersion} from "./app.actions";
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


}
