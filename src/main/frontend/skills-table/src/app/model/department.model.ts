import {OrgItem} from "./orgitem.model";
import {Team} from "./team.model";
import {User} from "./user.model";

export class Department extends OrgItem{
  teams: Team[];
  membersNoTeam: User[];

  constructor(
    id: number | null,
    name: string,
    about: string,
    leader: User | null,
    teams: Team[],
    membersNoTeam: User[]
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
