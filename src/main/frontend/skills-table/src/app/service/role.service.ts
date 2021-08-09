import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {SkillLevel} from "../model/skillLevel.model";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/users/';
  rolePath: string = '/role/';

  getAllRoles(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 0 + this.rolePath);
  }

  addRole(user: User, roleName: string): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + user.id + this.rolePath, {user, roleName});
  }

  deleteRole(userId: number, role: string): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + userId + this.rolePath + role);
  }

}
