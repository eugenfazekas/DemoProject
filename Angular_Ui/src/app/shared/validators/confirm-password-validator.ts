import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';

export class MyPasswordValidator {

      static ConformPasswordValidator(): ValidatorFn  {
        return (control: AbstractControl): {[key: string]: any} | null => {
            const password = control.get("password");
            const confirmPassword = control.get("confirmPassword");
            let err: ValidationErrors = {"passwordsNotEqual" : {  "actualValue": password.value}};
            if ((password.pristine && confirmPassword.pristine) || (password.value == '' && confirmPassword.value == '') ) {
              return null;
          }
        return password.value == confirmPassword.value  ? null: err;
            };
     }
}
