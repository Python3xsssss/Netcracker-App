import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {Skill} from "../model/skill.model";

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/skills/';

  getSkills(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getSkillById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  createSkill(skill: Skill): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, skill);
  }

  updateSkill(skill: Skill): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + skill.id, skill);
  }

  deleteSkill(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }
}
