

export class UserAccount {

  constructor(
    public id? : string,
    public email?: string,
    public	oldPassword?: string,
    public	password?: string,
    public mfa?: boolean
   ) {}
}
