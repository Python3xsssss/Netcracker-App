import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {Role} from "../model/role.model";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/users/';
  rolePath: string = '/role/';

  getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(this.baseUrl + 0 + this.rolePath);
  }

  addRole(user: User, roleName: string): Observable<Role> {
    return this.http.post<Role>(this.baseUrl + user.id + this.rolePath, {user, roleName});
  }

  deleteRole(userId: number, role: string): Observable<void> {
    return this.http.delete<void>(this.baseUrl + userId + this.rolePath + role);
  }

}
