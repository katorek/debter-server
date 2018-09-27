import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login.service";

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})

export class SigninComponent implements OnInit {
  public logged = this.loginService.isLogged();
  public user = this.loginService.getLoggedUser();

  constructor(private loginService: LoginService) {
  }


  ngOnInit() {
  }

  public socialSignIn(socialPlatform: string) {
    this.loginService.socialSignIn(socialPlatform).subscribe(
      ((loggedUser) => {
        this.logged = (loggedUser != null);
        console.log("Looged ?");
        console.log(this.logged);
        this.user = loggedUser;
      })
    );
  }

  public signOut(): void {
    this.loginService.signOut();
  }

  /*public socialSignIn(socialPlatform: string) {
    let socialPlatformProvider;
    if (socialPlatform == "facebook") {
      socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID
    } else if (socialPlatform == "google") {
      socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID
    }

    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {
        console.log(socialPlatform + " sign in data: ", userData);
        this.socialUser = userData;
        this.logged = true;
      }
    )
  }

  public signOut(): void {
    this.socialAuthService.signOut();
  }*/

}
