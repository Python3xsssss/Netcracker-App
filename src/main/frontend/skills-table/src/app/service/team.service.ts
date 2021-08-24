import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {Team} from "../model/team.model";

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/api/teams/';

  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(this.baseUrl);
  }

  getTeamById(id: number): Observable<Team> {
    return this.http.get<Team>(this.baseUrl + id);
  }

  createTeam(team: Team): Observable<Team> {
    return this.http.post<Team>(this.baseUrl, team);
  }

  updateTeam(team: Team): Observable<Team> {
    return this.http.put<Team>(this.baseUrl + team.id, team);
  }

  deleteTeam(id: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + id);
  }
}
