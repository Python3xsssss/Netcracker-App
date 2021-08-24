import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {Skill} from "../model/skill.model";

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/skills/';

  getSkills(): Observable<Skill[]> {
    return this.http.get<Skill[]>(this.baseUrl);
  }

  getSkillById(id: number): Observable<Skill> {
    return this.http.get<Skill>(this.baseUrl + id);
  }

  createSkill(skill: Skill): Observable<Skill> {
    return this.http.post<Skill>(this.baseUrl, skill);
  }

  updateSkill(skill: Skill): Observable<Skill> {
    return this.http.put<Skill>(this.baseUrl + skill.id, skill);
  }

  deleteSkill(id: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + id);
  }
}
