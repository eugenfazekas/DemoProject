import { Injectable } from '@angular/core';
import { UserModel } from '../model/user.model';
import { UserRestDataSourceService } from '../rest-api/user-rest-data-source.service';
import { LogService } from '../shared/services/log.service';
import { ImageService } from '../shared/services/image.service';
import { LoggedUserService } from '../shared/services/logged-user.service';
import { Observable } from 'rxjs';
import { UserAccount } from '../model/user.account';

@Injectable()
export class UserRepository {

  private user: UserModel = new UserModel();
  public loadedImages :boolean = false;

  constructor(private userRestDataSource: UserRestDataSourceService, private loggedUserService: LoggedUserService, private logservice: LogService,private imageService: ImageService,) {
      userRestDataSource.getUser().subscribe(
        res => { 
          this.user = res;
          this.user.profilePhotos == null ? this.user.profilePhotos = [] : this.user.profilePhotos = res.profilePhotos;
          this.loadedImages = true;
        },
        err => console.log(err)
      );
   }

   getUser() {
     return this.user;
   }

   updateUser(user: UserModel) {
    this.userRestDataSource.updateUser(user).subscribe(
      res => {
         this.user = res; 
         this.user.profilePhotos == null ? this.user.profilePhotos = [] : this.user.profilePhotos = res.profilePhotos;
         this.loggedUserService.setUser(user);
        },
      err =>console.log(err)
    )
   }

   saveProfileImage(image?: FormData, imageName?: string) {
    this.logservice.logDebugMessage(String('UserService saveImage() '));
    this.userRestDataSource.saveProfileImage(image).subscribe(
          res => { 
            this.user.profilePhotos.push(imageName);
            this.user.activeProfilePhoto == null || this.user.activeProfilePhoto == "" ? this.setActiveProfilePhoto(imageName) : null;
          },
            err => console.log(err) 
          );
     }

  deleteProfileImage(imageName: string) {
    let activeProfile =  this.user.activeProfilePhoto;
    let imageNameActive = imageName  == this.user.activeProfilePhoto ? 'true' : 'false';
    this.logservice.logDebugMessage(String('UserService deleteImage() '))
    this.userRestDataSource.deleteProfileImage(imageName, imageNameActive).subscribe(
          res => { 
            imageName == this.user.activeProfilePhoto ? this.user.activeProfilePhoto = null : activeProfile ;
            this.user.profilePhotos.splice(this.user.profilePhotos.findIndex(o => imageName == o), 1) 
          },
          err => console.log(err)
    );
  }

  setActiveProfilePhoto(imageName: string) {
    this.logservice.logDebugMessage(String('UserService setActiveProfilePhoto() '));
    this.userRestDataSource.setActiveProfilePhoto(imageName).subscribe(
              res => this.user.activeProfilePhoto = res,
              err => console.log(err)   
              );  
          }

   getProfilePhoto(): string {
      let profilePhoto = ( this.user.activeProfilePhoto != undefined && this.user.activeProfilePhoto != "" ) ? this.imageService._url +'/images/'+ this.user.id+'/' + this.user.activeProfilePhoto + '.png' :
      this.imageService._url + '/images/image/profile_placeholder.png';
        return profilePhoto;
   }
   
   updateAccount(user: UserAccount) : Observable<string> {
    return this.userRestDataSource.updateAccount(user);
   }
}
