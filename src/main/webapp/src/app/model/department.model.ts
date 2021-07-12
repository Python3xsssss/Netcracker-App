import {OrgItem} from "./orgitem.model";
import {Team} from "./team.model";
import {User} from "./user.model";

export class Department extends OrgItem{
  teams: Set<Team>;

  constructor(id: number, name: string, about: string, leader: User, teams: Set<Team>) {
    super(id, name, about, leader, null);
    this.teams = teams;
  }

}
