import { Injectable } from '@angular/core';
import { UserCredentials } from './user-credentials.model';
import { HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from '../constants/app.constants';
import { Observable } from 'rxjs';
import { JwtToken } from './jwt-token.model';
import {resetParseTemplateAsSourceFileForTest} from "@angular/compiler-cli/src/ngtsc/typecheck/diagnostics";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient
  ) { }

  authenticate(userCredentials: UserCredentials): Observable<JwtToken> {
    console.log(userCredentials)
    return this.http.post<JwtToken>(`${SERVER_API_URL}/api/authenticate`, userCredentials);
  }

  logout(): void {
    localStorage.removeItem('token');
  }
}
