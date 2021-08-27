import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';

import {TokenStorageService} from '../service/token-storage.service';
import {Observable, throwError} from 'rxjs';
import {catchError} from "rxjs/operators";
import {Router} from "@angular/router";

const TOKEN_HEADER_KEY = 'Authorization';
const BEARER_KEY = 'Bearer ';
const AUTH_URL = 'http://localhost:8080/api/auth/';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private tokenStorageService: TokenStorageService, private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const token = this.tokenStorageService.getToken();
    if (token != null) {
      authReq = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, BEARER_KEY + token)});
    }
    return next.handle(authReq).pipe(
      catchError((error) => {
        if (error.status === 401 && this.router.url !== '/auth/sign-in') {
          this.router.navigateByUrl('/home');
        }
        return throwError(error);
      })
    );
  }
}
