import {FormControl, FormGroup, Validators } from '@angular/forms';
import { UserExistValidator } from '../shared/validators/user-exist-form-validator';
import { UserRestDataSourceService } from '../rest-api/user-rest-data-source.service';
import { MyPasswordValidator } from '../shared/validators/confirm-password-validator';

export class UserRegistrationFormControl extends FormControl{

    label: string;
    modelProperty: string;

    constructor(label: string, property: string, value: any, validator: any, asyncValidator: any) {
        super(value,validator,asyncValidator);
        this.label = label;
        this.modelProperty = property;
    }

    getValidationMessages() {
        let messages: string[] = [];
        if (this.errors) {
            for (let errorName in this.errors) {
                switch (errorName) {
                    case "required":
                        messages.push(`You must enter a ${this.label}`);
                        break;
                    case "minlength":
                        messages.push(`A ${this.label} must be at least
                            ${this.errors['minlength'].requiredLength} characters`);
                        break;
                    case "maxlength":
                        messages.push(`A ${this.label} must be no more than
                            ${this.errors['maxlength'].requiredLength} characters`);
                        break;
                    case "pattern":
                        messages.push(`The ${this.label} field must be a valid ${this.label}`);
                        break;   
                    case "alreadyExist":
                        messages.push(`User with this email
                             ${this.errors['alreadyExist'].actualValue} allready registered `);
                        break;  
                }
            }
        }
        return messages;
    }
}

export class UserRegistrationFormGroup extends FormGroup {

        constructor(private _userRestDataSourceService: UserRestDataSourceService) {

            super({   
                    email: new UserRegistrationFormControl("Email","email","",Validators.compose([
                        Validators.required,
                        Validators.pattern("^[\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
                        Validators.minLength(5),
                        Validators.maxLength(30)
                        ]),
                    [UserExistValidator.userExistValidator(_userRestDataSourceService)]
                    ),

                    password: new UserRegistrationFormControl("Password","password","",Validators.compose([
                        Validators.required,
                        Validators.minLength(5),
                        Validators.maxLength(30)
                    ]),null),

                    confirmPassword: new UserRegistrationFormControl("ConfirmPassword","confirmPassword","",Validators.compose([
                        Validators.required,
                        Validators.minLength(5),
                        Validators.maxLength(30)
                    ]),null)
                    }, { validators: MyPasswordValidator.ConformPasswordValidator()  }
                )            
        }

        get userControls(): UserRegistrationFormControl[] {
            return Object.keys(this.controls)
                .map(k => this.controls[k] as UserRegistrationFormControl);
        }
    }