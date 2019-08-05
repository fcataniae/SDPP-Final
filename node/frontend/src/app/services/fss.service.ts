import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FssService {


  constructor( private _HTTP: HttpClient) { }

  public getSharedList() : Observable<any>{
    return this._HTTP.get<any>( environment.BACKEND_URL + 'files');
  }
}
