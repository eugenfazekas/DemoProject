import { FormControl,Validators } from '@angular/forms';

export class UserLoginFormModel {

   public email = new FormControl ('', [Validators.required, Validators.pattern("^[\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")]);
   public  password = new FormControl ('', [Validators.required]);
    
    getEmailErrorMessage() {
      if (this.email.hasError('required')) {
        return 'You must enter a value';
      }
  
      return this.email.hasError('pattern') ? 'Not a valid email' : '';
    }

    getPasswordErrorMessage() {
        if (this.password.hasError('required')) {
          return 'You must enter a password';
        }
  
      }
}
