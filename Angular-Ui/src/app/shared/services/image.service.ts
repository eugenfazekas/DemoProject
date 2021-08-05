import { Injectable, Inject } from '@angular/core';
import { LogService } from './log.service';
import { FormControl } from '@angular/forms';
import { RESOURCE_URL } from '../../rest-api/user-rest-data-source.service';

@Injectable()
export class ImageService {

  public imageBlob: Blob;
  public imageBase64;
  private imageWidths: number = 500;
  public _url: string; 

  constructor(private logservice: LogService, @Inject(RESOURCE_URL) _resourceURL: string,) {
    this.logservice.logDebugMessage(String('ImageService constructor: '));
    this._url = _resourceURL; }

  fileReader(item) {
    this.logservice.logDebugMessage(String('ImageService fileReader() '));
    return new Promise( (res)  => {
            let reader = new FileReader();
            reader.readAsDataURL(item);
            reader.onload = ( (data) => {
                     res(data.target.result);
            }
         )   
       }
    )
  }

 compressImage(src,width) {
  this.logservice.logDebugMessage(String('ImageService compressImage() '));
      return new Promise((res, rej) => {
        const img = new Image();
        img.src = src;
        img.onload = (event) => {
          const elem = document.createElement('canvas');
          const loadedImage: any = event.currentTarget;
          elem.width = width;
          elem.height = (loadedImage.width / loadedImage.height > 0 ? ( width / ( loadedImage.width / loadedImage.height )) : (width / (loadedImage.height / loadedImage.width)));
          const ctx = elem.getContext('2d');
          ctx.drawImage(img, 0, 0, elem.width, elem.height);
          const data = ctx.canvas.toDataURL();
          res(data);
        }
        img.onerror = error => rej(error);
      })
    }

  b64toBlob(dataURI) { 
    this.logservice.logDebugMessage(String('ImageService b64toBlob() '));    
        let byteString = atob(dataURI.split(',')[1]);
        let ab = new ArrayBuffer(byteString.length);
        let ia = new Uint8Array(ab);
    
        for (var i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ab], { type: 'image/png' });
    } 

    inputService(item : any) {
      this.fileReader(item).then(res => this.compressImage(res, this.imageWidths).then(res => { this.imageBlob = this.b64toBlob(res); this.imageBase64 = res}));
    }

    getBase64() {
      return this.imageBase64;
    }

    setBase64Null() {
      this.imageBase64 = null;
      return this.imageBase64;
    }
}
