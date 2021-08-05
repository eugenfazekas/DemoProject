import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MyPasswordValidator } from '../shared/validators/confirm-password-validator';

export class AccountUpdateFormModel extends FormControl {

label: string;
modelProperty: string;

    constructor(label: string, property: string, value: any, validator: any) {
        super(value,validator);
        this.label = label;
        this.modelProperty = property;
    }

    
    getValidationMessages() {
        let messages: string[] = [];
        if (this.errors) {
            for (let errorName in this.errors) {
                switch (errorName) {
                    case "required":
                        messages.push(`You must enter ${this.label}`);
                        break;
                    case "minlength":
                        messages.push(`${this.label} must be at least
                            ${this.errors['minlength'].requiredLength} characters`);
                        break;
                    case "maxlength":
                        messages.push(`A ${this.label} must be no more than
                            ${this.errors['maxlength'].requiredLength} characters`);
                        break;
                    case "pattern":
                        messages.push(`The ${this.label} field must be a valid ${this.label}`);
                        break;   
                }
            }
        }
        return messages;
    }
}

export class AccountUpdateFormModelGroup extends FormGroup {

    constructor() {

        super({   
                email: new AccountUpdateFormModel("Email","email","",Validators.compose([
                    Validators.pattern("^[\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
                    Validators.minLength(5),
                    Validators.maxLength(30)
                    ]),
                ),

                oldPassword: new AccountUpdateFormModel("ActualPassword","oldPassword","",Validators.compose([
                    Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(30)
                ])),

                password: new AccountUpdateFormModel("NewPassword","password","",Validators.compose([
                    Validators.minLength(5),
                    Validators.maxLength(30)
                ])),

                confirmPassword: new AccountUpdateFormModel("ConfirmNewPassword","confirmPassword","",Validators.compose([
                    Validators.minLength(5),
                    Validators.maxLength(30)
                ]))
                }, { validators: MyPasswordValidator.ConformPasswordValidator()  }
            )            
    }

        get userControls(): AccountUpdateFormModel[] {
            return Object.keys(this.controls)
                .map(k => this.controls[k] as AccountUpdateFormModel);
        }
    }
