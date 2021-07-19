import {Role} from "./role.model";
import {Department} from "./department.model";
import {Team} from "./team.model";
import {Position} from "./position.model";
import {SkillLevel} from "./skillLevel.model";

export class User {
  id: number;
  username: string;
  password: string;
  roles: Set<Role>;

  // Personal info
  firstName: string;
  lastName: string;
  age: number;
  email: string;
  about: string;

  // Work info
  department: Department;
  team: Team;
  position: Position;
  skillLevels: SkillLevel[] = [];


  constructor(id: number,
              username: string,
              password: string,
              roles: Set<Role>,
              firstName: string,
              lastName: string,
              age: number,
              email: string,
              about: string,
              department: Department,
              team: Team,
              position: Position,
              skillLevels: SkillLevel[]) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.email = email;
    this.about = about;
    this.department = department;
    this.team = team;
    this.position = position;
    this.skillLevels = skillLevels;
  }
}
