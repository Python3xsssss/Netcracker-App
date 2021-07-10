import {User} from "./user.model";
import {OrgItem} from "./orgitem.model";

export class Team extends OrgItem{
  members: Set<User>;

  constructor(id: number, name: string, about: string, leader: User, superior: OrgItem | null, members: Set<User>) {
    super(id, name, about, leader, superior);
    this.members = members;
  }
}
