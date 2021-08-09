import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthRequest} from "../model/auth.request";
import {TokenStorageService} from "./token-storage.service";
import {ApiResponse} from "../model/api.response";

const AUTH_API = 'http://localhost:8080/api/auth/';

@Injectable()
export class AuthService {
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
  }

  login(request: AuthRequest): Observable<ApiResponse> {
    //let request: AuthRequest = new AuthRequest(username, password);
    return this.http.post<ApiResponse>(AUTH_API + 'sign-in', request);

  }

}
