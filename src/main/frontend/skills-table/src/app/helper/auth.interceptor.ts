import {HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpHandler, HttpRequest} from '@angular/common/http';

import {TokenStorageService} from '../service/token-storage.service';
import {Observable, throwError} from 'rxjs';
import {catchError} from "rxjs/operators";
import {GlobalErrorHandler} from "./global-error-handler";

const TOKEN_HEADER_KEY = 'Authorization';
const BEARER_KEY = 'Bearer ';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private tokenStorageService: TokenStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const token = this.tokenStorageService.getToken();
    if (token != null) {
      authReq = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, BEARER_KEY + token)});
    }
    return next.handle(authReq).pipe(
      catchError((error) => {
        return throwError(error);
      })
    );
  }
}
