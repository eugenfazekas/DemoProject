import { UserAddress } from "./user.address";

export class UserStorage {
 				 
	constructor(
				 public firstName?: string,
				 public lastName?: string,
				 public fullName?: string,
				 public address?: UserAddress
				) {}
}