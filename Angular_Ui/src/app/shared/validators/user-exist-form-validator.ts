import { FormControl, AsyncValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { UserRestDataSourceService } from '../../rest-api/user-rest-data-source.service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

export class UserExistValidator {

  static userExistValidator(_userRestDataSourceService: UserRestDataSourceService): AsyncValidatorFn  {
    return (control: AbstractControl): Observable<ValidationErrors> => {
      let val = control.value;
      let err: ValidationErrors = {"alreadyExist" : {  "actualValue": val}};
            return ( _userRestDataSourceService.userExistCheck(val).pipe(map( res => {
               return  (res == true) ? err : null
                    } 
            ) ) ) 
          }
    }
}




