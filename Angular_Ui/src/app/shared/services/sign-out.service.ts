import { Injectable } from '@angular/core';
import { UserRestDataSourceService } from 'src/app/rest-api/user-rest-data-source.service';

@Injectable()
export class SignOutService {

  constructor( private userRestDataSourceService: UserRestDataSourceService) { }

  deleteCache() {
  this.userRestDataSourceService.deleteCache().subscribe(
    res => console.log(res),
    err => console.log(err)
  );
  }
}
