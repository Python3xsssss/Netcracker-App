export class Skill {
  id: number | null;
  name: string;
  about: string;


  constructor(id: number | null, name: string, about: string) {
    this.id = id;
    this.name = name;
    this.about = about;
  }

  public equals(obj: Skill): boolean {
    if (this == obj) return true;
    if (obj == null) return false;
    return this.id == obj.id;
  }
}
