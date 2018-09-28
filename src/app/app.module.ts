import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {DebtsComponent} from './debts/debts.component';
import {ExampleComponent} from './example/example.component';
import {environment} from '../environments/environment';

import {AuthServiceConfig, FacebookLoginProvider, SocialLoginModule} from 'angular-6-social-login-v2';
import {SigninComponent} from './signin/signin.component';
import {BsDropdownModule, ModalModule, TooltipModule} from 'ngx-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {CookieService} from 'ngx-cookie-service';
import {FormsModule} from '@angular/forms';
import {AuthService} from './auth/auth.service';
import {Router} from '@angular/router';
import 'rxjs/add/operator/finally';

// Configs
export function getAuthServiceConfigs() {
  return new AuthServiceConfig(
    [
      {
        id: FacebookLoginProvider.PROVIDER_ID,
        // provider: new FacebookLoginProvider("532650907182673")
        provider: new FacebookLoginProvider(environment.facebook_app_id)
      }/*,
      {
        id: GoogleLoginProvider.PROVIDER_ID,
        provider: new GoogleLoginProvider("Your-Google-Client-Id")
      }*/
    ]
  );
}

@NgModule({
  declarations: [
    AppComponent,
    DebtsComponent,
    ExampleComponent,
    SigninComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    SocialLoginModule,
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    FontAwesomeModule,
    FormsModule
  ],
  providers: [
    {
      provide: AuthServiceConfig,
      useFactory: getAuthServiceConfigs
    },
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(private auth: AuthService, private http: HttpClient, private router: Router) {
    this.auth.authenticate(undefined, undefined);
  }

  logout() {
    this.http.post('logout', {}).finally(() => {
      this.auth.authenticated = false;
      this.router.navigateByUrl('/login');
    }).subscribe();
  }
}
