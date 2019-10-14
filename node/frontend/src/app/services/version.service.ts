import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class VersionService {

  constructor(private _HTTP: HttpClient) { }

  getVersion(): Observable<any>{
    return this._HTTP.get<any>(environment.BACKEND_URL + 'version');
  }
}
