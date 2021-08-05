import { UserAddress } from "./user.address";

export class UserModel {
 				 
	constructor(
				 public id?: string,
				 public firstName?: string,
				 public lastName?: string,
				 public fullName?: string,
				 public date_registered?: string,
				 public activeProfilePhoto?: string,
				 public articlesId?: string[],
				 public profilePhotos?:  string[], 
				 public address?: UserAddress
				) {}
}
