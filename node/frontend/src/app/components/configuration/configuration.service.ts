import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {


  constructor( private _HTTP: HttpClient) { }

  public getConfigurations() : Observable<any>{
    return this._HTTP.get<any>( environment.BACKEND_URL + 'config/server');
  }
}
