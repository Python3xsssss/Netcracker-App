import {Injectable} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  getClientErrorMessage(error: Error): string {
    return error.message ?
      error.message :
      error.toString();
  }

  getServerErrorMessage(response: HttpErrorResponse): string {
    if (!navigator.onLine) {
      return 'No Internet Connection';
    }
    if (!response.error) {
      return response.message;
    }

    if (!response.error.message) {
      if (!response.error.error) {
        return response.message;
      }
      return response.error.error;
    }
    return response.error.message;
  }
}
