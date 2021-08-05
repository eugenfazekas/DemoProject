import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router} from "@angular/router";
import { LogService } from '../services/log.service';
import { LoggedUserService } from '../services/logged-user.service';

@Injectable()
export class AdminGuard {
  private jwt = new JwtHelperService();

  constructor(private loggedUserService: LoggedUserService, private router: Router,private logservice: LogService) { }
 
      canActivate(route: ActivatedRouteSnapshot,
          state: RouterStateSnapshot): boolean {   
            this.logservice.logDebugMessage(String('AdminGuard canActivate() ')); 
              let token = this.loggedUserService.getToken();
              let decodedToken = this.jwt.decodeToken(token);
              let userAdmin: boolean = false;
              if(token != '') {
                for(let auth of decodedToken.authorities){
                  if(auth == 'admin'){
                    userAdmin = true;
                  }
                }
              }
              if(!this.jwt.isTokenExpired(token) && userAdmin ) {
                return true ;
          } else {
            token = '';
          this.router.navigateByUrl('home');
                return false ;
      }
  }
}

