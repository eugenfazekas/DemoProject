import { Component } from '@angular/core';
import { UserRegistrationFormGroup } from 'src/app/model/userRegistrationForm.model';
import { UserRestDataSourceService } from 'src/app/rest-api/user-rest-data-source.service';
import { Router } from '@angular/router';
import { LogService } from 'src/app/shared/services/log.service';
import { UserAccount } from 'src/app/model/user.account';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

constructor(private _userRestDataSourceService: UserRestDataSourceService,
     private router: Router, private logservice: LogService) {
        this.logservice.logDebugMessage(String('RegistrationComponent constructor: '));
      }

newUser: UserAccount = new UserAccount();
mfaChecked: boolean = false;
formGroup: UserRegistrationFormGroup = new UserRegistrationFormGroup(this._userRestDataSourceService);

formSubmitted: boolean = false;

    submitForm() {
        Object.keys(this.formGroup.controls)
            .forEach(c => this.newUser[c] = this.formGroup.controls[c].value);
        this.formSubmitted = true;
        if (this.formGroup.valid) {
            this.logservice.logDebugMessage(String('RegistrationComponent submitForm() '));
            this.newUser.mfa = this.mfaChecked;
            this._userRestDataSourceService.saveUser(this.newUser).subscribe(
                res => {
                    this.router.navigateByUrl('')
                }
            )
        }
    } 
}