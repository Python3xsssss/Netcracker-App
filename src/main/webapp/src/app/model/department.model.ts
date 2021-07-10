import {OrgItem} from "./orgitem.model";
import {Team} from "./team.model";
import {User} from "./user.model";

export class Department extends OrgItem{
  teams: Set<Team>;

  constructor(id: number, name: string, about: string, leader: User, superior: OrgItem | null, teams: Set<Team>) {
    super(id, name, about, leader, superior);
    this.teams = teams;
  }

}
