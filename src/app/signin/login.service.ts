import {Injectable, OnInit} from '@angular/core';
import {AuthService, FacebookLoginProvider, SocialUser} from "angular-6-social-login-v2";
import {from, Observable} from "rxjs";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class LoginService implements OnInit {
  private user: SocialUser;
  private loggedIn: boolean;

  constructor(private socialAuthService: AuthService,
              private cookieService: CookieService) {
  }

  ngOnInit(): void {
    console.log("Token: " + this.cookieService.get('token'))
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
          this.cookieService.set('token', userData.token);
          return this.user;
        }
      )
    );

  }

  public signOut(): Observable<boolean> {
    return from(this.socialAuthService.signOut().then(
      ((data) => {
        console.log(data);
        this.user = null;
        this.loggedIn = false;
        this.cookieService.delete('token');
        return this.loggedIn;
      }), (err) => {
        alert(err);
        return true;
      }
    ))
  }

  public isLogged(): boolean {
    return this.loggedIn;
  }

  getLoggedUser() {
    return this.user;
  }


}
