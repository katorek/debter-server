import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {first} from "rxjs/operators";
import {UserService} from "../_services/user.service";
import {AlertService} from "../_services/alert.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  userForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private alertService: AlertService) {
  }

  get f() {
    return this.userForm.controls;
  }

  ngOnInit() {
    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(4)]],
      password: ['', [Validators.required, Validators.minLength(3)]]
    });

  }

  onSubmit() {
    if (this.userForm.invalid) {
      return
    }

    this.userService.register(this.userForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Rejestracja udana', true);
          this.router.navigate(['/login']);
        },
        error => {
          //todo
          this.alertService.error(error);
        });
  }
}
