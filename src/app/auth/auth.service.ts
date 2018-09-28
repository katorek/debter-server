import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  authenticated = false;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get('user', {headers: headers}).subscribe(response => {
      this.authenticated = !!response['name'];
      return callback && callback();
    });
  }
}
