import {User} from "./user.model";

export class OrgItem {
  id: number | null;
  name: string;

  about: string;

  leader: User | null;
  superior: OrgItem | null;


  constructor(id: number | null, name: string, about: string, leader: User | null, superior: OrgItem | null) {
    this.id = id;
    this.name = name;
    this.about = about;
    this.leader = leader;
    this.superior = superior;
  }

  public equals(obj: OrgItem): boolean {
    if (this == obj) return true;
    if (obj == null) return false;
    return this.id === obj.id && this.name === obj.name;
  }
}
