import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials = {username: '', password: ''};

  constructor(private auth: AuthService, private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
  }

  login() {
    this.auth.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/')
    });
    return false;
  }


}
