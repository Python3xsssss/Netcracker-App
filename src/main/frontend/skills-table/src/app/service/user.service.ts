import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {SkillLevel} from "../model/skillLevel.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/users/';
  skillLevelPath: string = '/skillLevels/';

  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getUserById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  createUser(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, user);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + user.id, user);
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }

  createSkillLevel(skillLevel: SkillLevel, userId: number): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + userId + this.skillLevelPath, skillLevel);
  }

  updateSkillLevel(skillLevel: SkillLevel, userId: number, skillLevelId: number): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + userId + this.skillLevelPath + skillLevelId, skillLevel);
  }

  deleteSkillLevel(userId: number, skillLevelId: number) {
    return this.http.delete<ApiResponse>(this.baseUrl + userId + this.skillLevelPath + skillLevelId)
  }
}
