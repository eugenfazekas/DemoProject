import { Component, OnInit, OnChanges, SimpleChanges, SimpleChange } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LogService } from 'src/app/shared/services/log.service';
import { LoggedUserService } from 'src/app/shared/services/logged-user.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private router: Router, public loggedUserService: LoggedUserService, private logservice: LogService ) {
    this.logservice.logDebugMessage(String('HeaderComponent constructor: admin = '+ this.loggedUserService.getAdmin()));
   }

  signOut() {
    this.logservice.logDebugMessage(String('HeaderComponent SignOut()'));
    this.loggedUserService.removeTokens();
    this.router.navigateByUrl('login');
    this.loggedUserService.setLoggedIn(false);
    this.loggedUserService.setFullName('user');
    window.location.reload();
  }
}
