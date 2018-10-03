import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../_services/authentication.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlertService} from "../_services/alert.service";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  credentials = {username: '', password: ''};
  returnUrl = '/home';
  loading = false;

  constructor(private authenticationService: AuthenticationService,
              private alertService: AlertService,
              private router: Router,
              private fb: FormBuilder) {
  }

  get f() {
    return this.loginForm.controls;
  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.authenticationService.logout();
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.credentials = {username: this.f.username.value, password: this.f.password.value};

    // this.login()
    debugger;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          debugger;
          this.router.navigate([this.returnUrl]);
        },
        error => {
          debugger;
          this.alertService.error(error);
          this.loading = false;
        });

  }

  login() {
    this.authenticationService.authenticate(this.credentials, () => {
      this.router.navigateByUrl(this.returnUrl);
      this.loading = false;
    });
    // this.alertService.error("Nie udało się zalogować");
    alert("Nie udało się zalogować");
    this.loading = false;
    this.loginForm.reset();
    return false;
  }

//  TODO


}
