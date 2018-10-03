import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  authenticated = false;
  loggedUser: string;

  constructor(private http: HttpClient,) {
  }

  /*logout() {
    return this.http.post('logout', {});
  }*/

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get('user', {headers: headers}).subscribe(response => {
      var a = 1;
      if (response['name']) {
        this.loggedUser = response['name'];
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }

      return callback && callback();
    });
  }

  getLoggedUser() {
    return this.loggedUser;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${environment.apiUrl}/auth/signin`, {username: username, password: password})
      .pipe(map(user => {
        debugger;
        // login successful if there's a jwt token in the response
        if (user && user.accessToken) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
        }

        return user;
      }));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }


  /*login(username: string, password: string) {
    return this.http.post<any>(`${config.apiUrl}/users/authenticate`, { username: username, password: password })
      .pipe(map(user => {
        // login successful if there's a jwt token in the response
        if (user && user.token) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
        }

        return user;
      }));
  }*/

  /*logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }*/
}
