import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {SkillLevel} from "../model/skillLevel.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/users/';
  skillLevelPath: string = '/skillLevels/';

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(this.baseUrl + id);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl, user);
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(this.baseUrl + user.id, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + id);
  }

  createSkillLevel(skillLevel: SkillLevel, userId: number): Observable<SkillLevel> {
    return this.http.post<SkillLevel>(this.baseUrl + userId + this.skillLevelPath, skillLevel);
  }

  updateSkillLevel(skillLevel: SkillLevel, userId: number, skillLevelId: number): Observable<SkillLevel> {
    return this.http.put<SkillLevel>(this.baseUrl + userId + this.skillLevelPath + skillLevelId, skillLevel);
  }

  deleteSkillLevel(userId: number, skillLevelId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + userId + this.skillLevelPath + skillLevelId)
  }
}
