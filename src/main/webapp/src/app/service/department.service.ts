import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {Department} from "../model/department.model";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/departments/';

  getDepartments(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getDepartmentById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  createDepartment(department: Department): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, department);
  }

  updateDepartment(department: Department): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + department.id, department);
  }

  deleteDepartment(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }
}
