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

  public doSearch(params: any[]){
    return this._HTTP.get<any>(environment.BACKEND_URL + 'searchs?' + FssService.buildParams(params) );
  }

  public postFile(file: any):Observable<any>{
    const data = new FormData();
    data.append('file', file,file.name);
    return this._HTTP.post(environment.BACKEND_URL + 'upload/file',data,{
      reportProgress: true,
      observe: 'events'
    });
  }


  private static buildParams(params: any[]) : string{

    var searchParam = '';

    var index = 0;
    var maxIndex = params.length;

    do{
      searchParam += params[index].key + '=' + params[index].value;
      index++;
      if(index < maxIndex)
        searchParam += '&';
    }while(index < maxIndex);


    return searchParam;
  }
}
