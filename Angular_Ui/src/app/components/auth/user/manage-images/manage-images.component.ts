import { Component, OnInit } from '@angular/core';
import { UserRepository } from 'src/app/repository/user-repository';
import { ImageService } from 'src/app/shared/services/image.service';
import { UserModel } from 'src/app/model/user.model';
import { FormBuilder, Validators } from '@angular/forms';
import { LogService } from 'src/app/shared/services/log.service';
import { formatDate } from '@angular/common';
import { LoggedUserService } from 'src/app/shared/services/logged-user.service';

@Component({
  selector: 'app-manage-images',
  templateUrl: './manage-images.component.html',
  styleUrls: ['./manage-images.component.css']
})
export class ManageImagesComponent implements OnInit {

  id:string;
  firstName: string;
  userModel: UserModel = new UserModel();
  editProfile: boolean = false;
  formSubmitted: boolean = false;
  hide = true;
  accept: string[] = ['.jpg','.png','.jpeg'];
  multiple:boolean = false;
  imageIndex: number = 0;
  
  constructor(private loggedUserService: LoggedUserService, private formBuilder: FormBuilder, private logService: LogService,
                public userRepository: UserRepository, public imageService: ImageService) {
                  this.id = loggedUserService.getId();
                  this.firstName = loggedUserService.getUser() != null ? loggedUserService.getUser().firstName : 'User';
                 }

  ngOnInit(): void {
  }

  imageUploadForm = this.formBuilder.group({  
    image: ['',[Validators.pattern('[^Ω]+'),Validators.required]], 
  })
       

onChange(event) {
this.logService.logDebugMessage(String('EditUserDetailsComponent onChange() '));
this.imageService.inputService(this.imageUploadForm.controls['image'].value);
}

getImageTitle(id: string): string {
this.logService.logDebugMessage(String('EditUserDetailsComponent getImageTitle() ')); 
let image_title = id + 'Ω' + formatDate(new Date(),'yyyy.MM.dd HH-mm-ss','en');
return image_title;
}

submitImageForm() {
      let id =  this.loggedUserService.getId(); 
      let imageTitle = this.getImageTitle(id);
        if(this.imageUploadForm.valid) {
          let formData = new FormData();
          formData.append('fileInput', this.imageService.imageBlob, imageTitle);
          this.userRepository.saveProfileImage(formData,imageTitle);
          this.imageUploadForm.controls['image'].patchValue('');
          this.imageService.setBase64Null();
    } 
}

updateImageProfile(imageName: string) {
this.userRepository.setActiveProfilePhoto(imageName);
}

deleteProfileImage(imageName: string) {
this.userRepository.deleteProfileImage(imageName);
this.imageIndex != 0 ? this.imageIndex = this.imageIndex - 1: this.imageIndex = this.imageIndex + 1;
    }

previousImage() {
  let actualImageIndex = this.imageIndex;
  this.imageIndex > 0 ? this.imageIndex = actualImageIndex - 1 : this.imageIndex = actualImageIndex;
    }

nextImage() {
  let actualImageIndex = this.imageIndex;
  this.imageIndex < this.userRepository.getUser().profilePhotos.length - 1 ? this.imageIndex = actualImageIndex + 1 : this.imageIndex = actualImageIndex;
    }
    
setImageIndex(index: number) {
  this.imageIndex = index;
  }    

}
