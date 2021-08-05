import { Injectable } from '@angular/core';
import { LogService } from './log.service';
import { UserStorage } from 'src/app/model/user.storage';
import { UserModel } from 'src/app/model/user.model';
import { HttpHeaders } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable()
export class LoggedUserService {

  public loggedIn: boolean = false;
  private auth_token: string;
  private id: string;
  private user: UserStorage;
  public fullName: string = 'user';
  private admin: boolean = false;
  private helper = new JwtHelperService();
  private mfa: boolean = false;

  constructor(private logservice: LogService) {
    this.initStorage();
    this.loggedIn = this.auth_token != '' ?  true : false;
    this.fullName = this.user != null ?  this.user.fullName : 'user';
    this.auth_token != '' ? this.adminCheck(this.auth_token) : false;
    this.checkTokenExpiration();
   }


initStorage() {
  this.logservice.logDebugMessage(String('TokenService getLocalToken()'));
  this.auth_token = localStorage.getItem('token') != null ? localStorage.getItem('token') : '';  
  this.id = localStorage.getItem('id') != null ? localStorage.getItem('id') : '';   
  let user = localStorage.getItem('user') != null ? localStorage.getItem('user') : '';
  this.user = user != '' ? JSON.parse(user) : user = null;
  localStorage.getItem('mfa') == 'true' ? this.mfa = true : this.mfa = false;  
     return ;
 }

 removeTokens() {
    this.logservice.logDebugMessage(String('LocalStorageService removeToken() '));
    localStorage.removeItem('token');
    localStorage.removeItem('id');
    localStorage.removeItem('user');
    localStorage.removeItem('mfa');
    this.auth_token = '';
    this.id = '';
    this.user = null;
    this.mfa = null;
  }

  getId() {
    return this.id;
  }

  setId(id: string) {
    localStorage.setItem('id', id);
    this.id = id;
  }

  getToken() {
    return this.auth_token;
  }

  setToken(token: string) {
    this.logservice.logDebugMessage(String('TokenService setToken()'));
    this.auth_token = token;
    localStorage.setItem('token', token);  
 }

  getUser() {
  return this.user;
}

  setUser(user: UserModel) {
    this.user = new UserStorage();
    this.user.fullName = user.firstName + ' ' +user.lastName;
    Object.keys(user).forEach(c => { if(user[c] != undefined)  this.user[c] = user[c] } );
    localStorage.setItem('user', JSON.stringify(this.user));
    this.setFullName(this.user.fullName);
}

  getAdmin() {
    return this.admin;
  }
  getFullName() {
    return  this.fullName;
  }

  setFullName(fullName: string) {
    this.fullName = fullName;
  }

  getLoggedIn() {
    return this.loggedIn;
  }

  setLoggedIn(loggedIn: boolean) {
    this.loggedIn = loggedIn;
  }

  getOptions() {
    this.logservice.logDebugMessage(String('TokenService getOptions()'));
    return new HttpHeaders()
        .set('Authorization', `Bearer ${this.auth_token}`)
        }

  adminCheck(token: any) {
    let decodedToken = this.helper.decodeToken(token);
    this.logservice.logDebugMessage(String('TokenService adminCheck()'));
    if(decodedToken.authorities != undefined) {
    let authorities = decodedToken.authorities;
    for(let auth of authorities){
      auth == 'admin' ?  this.admin = true : null;
     ( auth == 'admin' && this.user == null ) ? this.fullName = 'admin' : null ;
          }
        }
    }

  checkTokenExpiration() {
        this.auth_token != '' ? 
                          !this.helper.isTokenExpired(this.auth_token) ? this.loggedIn = true : this.loggedIn = false 
                                      :
                                              null;
  }  

  setMfa(mfa: string) {
   mfa == 'true' ? this.mfa = true : this.mfa = false;
   localStorage.setItem('mfa', mfa);
  }

  getMfa() {
    return this.mfa;
  }
}