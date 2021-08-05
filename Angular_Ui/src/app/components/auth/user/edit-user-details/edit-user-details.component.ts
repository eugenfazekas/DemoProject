import { Component, OnInit } from '@angular/core';
import { UserRepository } from 'src/app/repository/user-repository';
import { FormBuilder, Validators } from '@angular/forms';
import { UserModel } from 'src/app/model/user.model';

import { LogService } from 'src/app/shared/services/log.service';
import { ImageService } from 'src/app/shared/services/image.service';
import { LoggedUserService } from 'src/app/shared/services/logged-user.service';
import { MyPasswordValidator } from 'src/app/shared/validators/confirm-password-validator';
import { UserAccount } from 'src/app/model/user.account';
import { AccountUpdateFormModelGroup } from 'src/app/model/accout-update-form.model';

@Component({
  selector: 'app-edit-user-details',
  templateUrl: './edit-user-details.component.html',
  styleUrls: ['./edit-user-details.component.css']
})
export class EditUserDetailsComponent {

  firstName: string = 'User';
  userModel : UserModel = new UserModel();
  editProfile: boolean = false;
  editAccount: boolean = false;
  formSubmitted: boolean = false;
  hide = true;
  userAccount: UserAccount = new UserAccount();
  formGroup: AccountUpdateFormModelGroup = new AccountUpdateFormModelGroup();
  responseText: string;
  responseToggle: boolean = false;
  mfaChecked: boolean;

  constructor(public userRepository: UserRepository, private formBuilder: FormBuilder, private loggedUserService: LoggedUserService,
      private logservice: LogService, public imageService: ImageService) {
      this.logservice.logDebugMessage(String('EditUserDetailsComponent constructor: '));
      this.firstName = loggedUserService.getUser() != null ? loggedUserService.getUser().firstName : 'User';
      this.mfaChecked = loggedUserService.getMfa();
  }

    editUserDetailsForm = this.formBuilder.group({ 
            firstName: ['',[ Validators.minLength(3),Validators.maxLength(20)]],
            lastName: ['',[ Validators.minLength(3),Validators.maxLength(20)]],
            address : this.formBuilder.group({
                country: ['',[ Validators.minLength(2),Validators.maxLength(20)]],
                city: ['',[ Validators.minLength(3),Validators.maxLength(20)]],
                street: ['',[ Validators.minLength(3),Validators.maxLength(20)]],
                number: ['',[ Validators.minLength(1),Validators.maxLength(20)]],
             })
        });

     pacthEditUserDetailsForm(res: UserModel) {
          this.editUserDetailsForm.controls['firstName'].patchValue(res.firstName);
          this.editUserDetailsForm.controls['lastName'].patchValue(res.lastName);
          res.address ? this.editUserDetailsForm.patchValue({address : { country : res.address.country }}) : '';
          res.address ? this.editUserDetailsForm.patchValue({address : { city : res.address.city }}): '';
          res.address ? this.editUserDetailsForm.patchValue({address : { street : res.address.street }}): '';
          res.address ? this.editUserDetailsForm.patchValue({address : { number : res.address.number }}): '';
     }  

     enableEditProfile() {
      this.logservice.logDebugMessage(String('EditUserDetailsComponent enableEdit()'));
      this.editProfile = true;
      this.editAccount = false;
      this.pacthEditUserDetailsForm(this.userRepository.getUser());
    }

    enableEditAccount() {
      this.editAccount = true;
      this.editProfile = false;
    }

    submitProfileForm() {
      this.userModel.id = this.loggedUserService.getId(); 
      Object.keys(this.editUserDetailsForm.controls)
            .forEach(c => this.userModel[c] = this.editUserDetailsForm.controls[c].value);
      if(this.editUserDetailsForm.valid){
        this.logservice.logDebugMessage(String('EditUserDetailsComponent submitForm() '));
        this.userRepository.updateUser(this.userModel);
        this.formSubmitted = true;
        this.editProfile = false;
      } 
    } 

    submitAccountForm() {
      this.userAccount = new UserAccount();
      if (this.formGroup.valid) {
          this.logservice.logDebugMessage(String('RegistrationComponent submitForm() '));
          Object.keys(this.formGroup.controls).forEach(c => this.userAccount[c] = this.formGroup.controls[c].value);
          this.userAccount.id = this.userRepository.getUser().id;
          this.userAccount.mfa = this.mfaChecked; console.log(this.userAccount)
          this.userRepository.updateAccount(this.userAccount).subscribe(
            res => {
               this.responseText = res ;
                this.responseText == 'User have been updated!' ? this.responseToggle = true : null; 
               },
            err => console.log(err)
          );
          this.formSubmitted = true;
          this.editAccount = false;
      }     
    } 

    hideVisibility(formControll: string): string {

      if(this.hide == false) {
        return 'text';
      }
      else if (this.hide && formControll != 'email') {
          return 'password';
      }
    }

    updateOk() {
      this.responseToggle = false;
    }
}