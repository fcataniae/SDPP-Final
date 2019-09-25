import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class VersionService {

  constructor(private _HTTP: HttpClient) { }

  getVersion(): Observable<string>{
    return this._HTTP.get<string>(environment.BACKEND_URL + 'version');
  }
}
