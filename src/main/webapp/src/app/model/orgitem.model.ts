import {User} from "./user.model";

export class OrgItem {
  id: number;
  name: string;

  about: string;

  leader: User;
  superior: OrgItem | null;


  constructor(id: number, name: string, about: string, leader: User, superior: OrgItem | null) {
    this.id = id;
    this.name = name;
    this.about = about;
    this.leader = leader;
    this.superior = superior;
  }
}
