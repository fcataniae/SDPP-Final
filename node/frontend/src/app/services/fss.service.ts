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

  public getBinaryFile(path: string) : Observable<any>{
    return this._HTTP.get<any>( environment.BACKEND_URL + 'file/' + path);
  }

  public doSearch(params){
    return this._HTTP.get<any>(environment.BACKEND_URL + 'search/files',{ params: params } );
  }

  public getUploadUrl(): string{
    return environment.BACKEND_URL + 'upload/file';
  }

}
