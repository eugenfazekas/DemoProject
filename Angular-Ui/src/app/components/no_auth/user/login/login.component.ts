import { Component, ViewChild } from '@angular/core';
import { UserLoginFormModel } from 'src/app/model/user-login-form.model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { LogService } from 'src/app/shared/services/log.service';
import { UserRestDataSourceService } from 'src/app/rest-api/user-rest-data-source.service';
import { LoggedUserService } from 'src/app/shared/services/logged-user.service';
import { MatStepper } from '@angular/material/stepper';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  userLoginFormModel: UserLoginFormModel = new UserLoginFormModel();
  mfaChecked: boolean = false;
  private email: string;
  errorMessageCheck: boolean = false;

  @ViewChild('stepper') private myStepper: MatStepper;
  
  constructor(private authService: AuthService, private router: Router, 
              private formBuilder: FormBuilder, private logservice: LogService, private userRestDataSourceService: UserRestDataSourceService, private loggedUserService: LoggedUserService, ) {
                this.logservice.logDebugMessage(String('LoginComponent constructor: '));
  }
 
  mfaCheckForm = this.formBuilder.group({
                                           email: new FormControl('', [Validators.required, Validators.pattern("^[\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")]) 
                                        });

  loginForm = this.formBuilder.group({
                                          password: new FormControl('', [Validators.required,Validators.minLength(3)])
                                      });

  submitMfaForm() {
    if(this.mfaCheckForm) {
      this.authService.mfaCheck(this.mfaCheckForm.controls['email'].value).subscribe( 
            res => {
                      res != 'User Not Exist!' ? this.mfaChecked = this.errorMessageCheck = true : null;
                      this.email = this.mfaCheckForm.controls['email'].value;
                      res != 'User Not Exist!' && res == 'true' ? this.mfaChecked = true : this.mfaChecked = false ;
                      res == 'User Not Exist!' ?   this.errorMessageCheck = true  : this.errorMessageCheck = false; 
                      res != 'User Not Exist!' ? this.myStepper.next() : null;
                      res != 'User Not Exist!' ? this.loggedUserService.setMfa(res) : null;
                   }
      )
    }
  }
  submitForm() {
    if(this.loginForm.valid) {
      this.logservice.logDebugMessage(String('LoginComponent submitForm() '));
      this.authService.loginUser(this.email, this.loginForm.controls['password'].value).subscribe(
            res =>  {  res != null ? this.loggedUserService.setLoggedIn(true) : null; 
                      this.errorMessageCheck = false
                      this.userRestDataSourceService.getUser().subscribe(                           
                              res => {  
                                 if(res.firstName != null) {this.loggedUserService.setUser(res)}; 
                                 this.loginForm.reset;
                                 return  res != null ?  
                                                res.firstName  == null ?  this.router.navigateByUrl('firstSteps') : this.router.navigateByUrl('') // return this
                                                                                                :             
                                                                                                          null  // or return this                                                         
                                     }
                                                                          )   
                    },
                      err => {
                                this.errorMessageCheck = true; 
                             }
           ) 
       }  
    }

    generateOtpForm() {
      if(this.loginForm.valid) {
        this.logservice.logDebugMessage(String('LoginComponent submitForm() '));
        this.authService.generateOneTimePassword(this.email, this.loginForm.controls['password'].value).subscribe(
                                res => {
                                        if(res == 'One time password send') {
                                          this.myStepper.next();
                                                                        }
                                       res == 'Invalid username or password' ? this.errorMessageCheck = true : this.errorMessageCheck = false;
                                       this.loginForm.reset;                                    
                                       }
                                  )
                              }
                       }

    goBack(){
        this.myStepper.previous();
    }
    
    goForward(){
        this.myStepper.next();
    }

    delegateForm() {
      this.mfaChecked ? this.generateOtpForm() : this.submitForm();
    }
  }

