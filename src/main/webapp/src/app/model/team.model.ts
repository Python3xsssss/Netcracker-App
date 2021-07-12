import {User} from "./user.model";
import {OrgItem} from "./orgitem.model";
import {Department} from "./department.model";

export class Team extends OrgItem{
  superior: Department;
  members: Set<User>;

  constructor(id: number, name: string, about: string, leader: User, superior: Department, members: Set<User>) {
    super(id, name, about, leader, superior);
    this.members = members;
    this.superior = superior;
  }
}
