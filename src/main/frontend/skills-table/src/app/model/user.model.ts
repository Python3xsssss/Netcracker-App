import {Role} from "./role.model";
import {Department} from "./department.model";
import {Team} from "./team.model";
import {Position} from "./position.model";
import {SkillLevel} from "./skillLevel.model";

export class User {
  id: number | null;
  username: string;
  password: string;
  roles: string[] = [];

  // Personal info
  firstName: string;
  lastName: string;
  email: string;
  about: string;

  // Work info
  department: Department | null;
  team: Team | null;
  position: Position;
  skillLevels: SkillLevel[] = [];

  //Other info
  isNonLocked: boolean;
  isActive: boolean;


  constructor(
    id: number | null,
    username: string,
    password: string,
    roles: string[],
    firstName: string,
    lastName: string,
    age: number,
    email: string,
    about: string,
    department: Department | null,
    team: Team | null,
    position: Position,
    skillLevels: SkillLevel[],
    isNonLocked: boolean,
    isActive: boolean
  ) {
    this.id = id;
    this.username = username;
    this.password = password;
    if (this.roles.length > 0) {
      this.roles = roles;
    }
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.about = about;
    this.department = department;
    this.team = team;
    this.position = position;
    if (this.skillLevels.length > 0) {
      this.skillLevels = skillLevels;
    }
    this.isNonLocked = isNonLocked;
    this.isActive = isActive;
  }

  public equals(obj: User): boolean {
    if (this == obj) return true;
    if (obj == null) return false;
    return this.id == obj.id;
  }
}
