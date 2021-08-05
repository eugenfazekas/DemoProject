import { Injectable, Inject } from '@angular/core';
import { RESOURCE_URL } from './user-rest-data-source.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogService } from '../shared/services/log.service';

import { LoggedUserService } from '../shared/services/logged-user.service';



@Injectable()
export class CategoriesRestDataSourceService {

  private baseURL: string ;
  
  private param(param: string) {
        return new HttpParams().set('category', param)
  }

  constructor( private _http: HttpClient, @Inject(RESOURCE_URL) _baseURL: string, private loggedUserService: LoggedUserService, private logservice: LogService ) {
    this.baseURL = _baseURL;
    this.logservice.logDebugMessage(String('CategoriesRestDataSourceService constructor: ')); 
  }

    addCategory(category: string): Observable<string> { 
      this.logservice.logDebugMessage(String('CategoriesRestDataSourceService addCategory() '));
      return this._http.post(`${this.baseURL}/categories/addCategory`,{},{'headers': this.loggedUserService.getOptions(), 'params' : this.param(category) , responseType: 'text'})
    }

    deleteCategory(category: string): Observable<string> {
      this.logservice.logDebugMessage(String('CategoriesRestDataSourceService deleteCategory() '));
      return this._http.post(`${this.baseURL}/categories/deleteCategory`,{},{'headers': this.loggedUserService.getOptions(), 'params' : this.param(category) , responseType: 'text'});
    }

    getCategories(): Observable<string[]> {
      this.logservice.logDebugMessage(String('CategoriesRestDataSourceService getCategories() '));
      return this._http.get<string[]>(`${this.baseURL}/categories/findCategories`,{ 'headers': this.loggedUserService.getOptions() });
   }
}

