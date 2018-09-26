import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-example',
  templateUrl: './example.component.html',
  styleUrls: ['./example.component.css']
})
export class ExampleComponent implements OnInit {

  title = 'Demo app';
  data = {};

  constructor(private http: HttpClient) {
    http.get('resource').subscribe(data => this.data = data);
  }

  ngOnInit() {
  }

}
