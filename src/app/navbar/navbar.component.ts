import {Component} from '@angular/core';
import {AuthenticationService} from "../_services/authentication.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  navbarOpen = false;

  constructor(private auth: AuthenticationService, private http: HttpClient, private router: Router) {
    this.auth.authenticate(undefined, undefined);
  }

  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

  logout() {
    this.http.post('logout', {}).finally(() => {
      this.auth.authenticated = false;
      this.router.navigateByUrl('/login');
    }).subscribe();
  }

  authenticated() {
    return this.auth.authenticated;
  }

}
