import {User} from "./user.model";
import {OrgItem} from "./orgitem.model";
import {Department} from "./department.model";

export class Team extends OrgItem{
  superior: Department;
  members: User[];

  constructor(id: number | null, name: string, about: string, leader: User | null, superior: Department, members: User[]) {
    super(id, name, about, leader, superior);
    this.members = members;
    this.superior = superior;
  }

  public equals(obj: Team): boolean {
    if (this == obj) return true;
    return super.equals(obj);
  }
}
