import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {Department} from "../model/department.model";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/departments/';

  getDepartments(): Observable<Department[]> {
    return this.http.get<Department[]>(this.baseUrl);
  }

  getDepartmentById(id: number): Observable<Department> {
    return this.http.get<Department>(this.baseUrl + id);
  }

  createDepartment(department: Department): Observable<Department> {
    return this.http.post<Department>(this.baseUrl, department);
  }

  updateDepartment(department: Department): Observable<Department> {
    return this.http.put<Department>(this.baseUrl + department.id, department);
  }

  deleteDepartment(id: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + id);
  }
}
