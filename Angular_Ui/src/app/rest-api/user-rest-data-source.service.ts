import { Injectable,  Inject, InjectionToken } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogService } from '../shared/services/log.service';
import { UserAccount } from '../model/user.account';
import { UserModel } from '../model/user.model';
import { LoggedUserService } from '../shared/services/logged-user.service';

export const AUTH_URL = new InjectionToken<string>('AuthUrl');
export const RESOURCE_URL = new InjectionToken<string>('ResourceUrl');

@Injectable()
export class UserRestDataSourceService {

  private _authURL: string ;
  private _resourceURL: string ;

   param1(email: string) { 
     return new HttpParams()
            .set('email', email);
   }

   param2(imageName: string) { 
    return new HttpParams()
           .set('imageName', imageName);     
  }

  param3(imageName: string, imageNameActive: string) { 
    return new HttpParams()
           .set('imageName', imageName)
           .set('imageNameActive', imageNameActive);
                 
  }
   
  constructor(private loggedUserService: LoggedUserService, private _http: HttpClient, @Inject(AUTH_URL) _authURL: string, @Inject(RESOURCE_URL) _resourceURL: string, private logservice: LogService) {
           this.logservice.logDebugMessage(String('UserRestDataSourceService constructor: '));
           this._authURL = _authURL; 
           this._resourceURL = _resourceURL; 
   }

  saveUser(user: UserAccount): Observable<String> {
    this.logservice.logDebugMessage(String('UserRestDataSourceService saveUser() '));
    return this._http.post(`${this._authURL}/user/registerUser`,user,{ responseType: 'text'});
  }

  userExistCheck(email: string): Observable<boolean>{
    this.logservice.logDebugMessage(String('UserRestDataSourceService userExistCheck() '));
    return this._http.post<boolean>(`${this._authURL}/user/userExistCheck`, this.param1(email));
  }

  getUser():Observable<UserModel> {
    this.logservice.logDebugMessage(String('UserRestDataSourceService getUser() '));
    return this._http.post<UserModel>(`${this._resourceURL}/user/getUser`,{},{'headers': this.loggedUserService.getOptions()});
  }

  updateUser(user: UserModel) {
    this.logservice.logDebugMessage(String('UserRestDataSourceService updateUser() '));
    return this._http.post<UserModel>(`${this._resourceURL}/user/updateUser`, user,{'headers': this.loggedUserService.getOptions()});
  }

  saveProfileImage(image: FormData): Observable<string> {
    this.logservice.logDebugMessage(String('UserRestDataSourceService saveImage() '));
    return this._http.post(`${this._resourceURL}/userDetails/saveProfileImage`,image, {'headers': this.loggedUserService.getOptions(), responseType: 'text'});
  }

  deleteProfileImage(imageName: string, imageNameActive: string ): Observable<string> {
    this.logservice.logDebugMessage(String('UserRestDataSourceService saveImage() '));
    return this._http.post(`${this._resourceURL}/userDetails/deleteProfileImage`,{},{'headers': this.loggedUserService.getOptions(), 'params' : this.param3(imageName, imageNameActive),responseType: 'text'});
  }

  setActiveProfilePhoto(imageName: string): Observable<string> {
    this.logservice.logDebugMessage(String('UserRestDataSourceService saveImage() '));
    return this._http.post(`${this._resourceURL}/userDetails/setActiveProfilePhoto`,{},{'headers': this.loggedUserService.getOptions(), 'params' : this.param2(imageName), responseType: 'text'});
  }

  updateAccount(user: UserAccount): Observable<string> {
    this.logservice.logDebugMessage(String('UserRestDataSourceService updateUser() '));
    return this._http.post(`${this._authURL}/user/updateUserAccount`, user,{responseType: 'text'});
  }
}
 
