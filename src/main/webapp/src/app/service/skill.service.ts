import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";

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

  createSkill(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, user);
  }

  updateSkill(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + user.id, user);
  }

  deleteSkill(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }
}
