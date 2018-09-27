import {Injectable} from '@angular/core';
import {AuthService, FacebookLoginProvider, SocialUser} from "angular-6-social-login-v2";
import {from, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private user: SocialUser;
  private loggedIn: boolean;

  constructor(private socialAuthService: AuthService) {
  }

  public socialSignIn(socialPlatform: string): Observable<SocialUser> {
    let socialPlatformProvider;
    if (socialPlatform == "facebook") {
      socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID
    }
    /*else if (socialPlatform == "google") {
         socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID
       }*/

    return from(
      this.socialAuthService.signIn(socialPlatformProvider).then(
        (userData) => {
          console.log(socialPlatform + " sign in data: ", userData);
          this.user = userData;
          this.loggedIn = true;
          return this.user;
        }
      )
    );

  }

  public signOut(): void {
    this.socialAuthService.signOut();
  }

  public isLogged(): boolean {
    return this.loggedIn;
  }

  getLoggedUser() {
    return this.user;
  }
}
