import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {Team} from "../model/team.model";

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/teams/';

  getTeams(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getTeamById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  createTeam(team: Team): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, team);
  }

  updateTeam(team: Team): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + team.id, team);
  }

  deleteTeam(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }
}
