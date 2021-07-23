import {OrgItem} from "./orgitem.model";
import {Team} from "./team.model";
import {User} from "./user.model";

export class Department extends OrgItem{
  teams: Set<Team>;
  membersNoTeam: Set<User>;

  constructor(
    id: number,
    name: string,
    about: string,
    leader: User,
    teams: Set<Team>,
    membersNoTeam: Set<User>
  ) {
    super(id, name, about, leader, null);
    this.teams = teams;
    this.membersNoTeam = membersNoTeam;
  }

  public equals(obj: Department): boolean {
    if (this == obj) return true;
    return super.equals(obj);
  }

}
