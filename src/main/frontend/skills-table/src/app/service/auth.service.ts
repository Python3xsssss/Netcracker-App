import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthRequest} from "../model/auth.request";
import {TokenStorageService} from "./token-storage.service";
import {User} from "../model/user.model";

const AUTH_API = 'http://localhost:8080/api/auth/';

@Injectable()
export class AuthService {
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
  }

  login(request: AuthRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(AUTH_API + 'sign-in', request);
  }

}

class JwtResponse {
  jwtToken: string;
  user: User;

  constructor(jwtToken: string, user: User) {
    this.jwtToken = jwtToken;
    this.user = user;
  }
}
