import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  title = 'Menedżerze długów';
  data: Data = new Data();


  constructor(private auth: AuthService, private http: HttpClient) {
    http.get('resource').subscribe(data => this.data = data as Data);
  }

  authenticated() {
    return this.auth.authenticated;
  }

}

export class Data {
  public id = 0;
  public content = '';
}
