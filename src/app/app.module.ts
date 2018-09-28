import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {DebtsComponent} from './debts/debts.component';
import {ExampleComponent} from './example/example.component';
import {environment} from '../environments/environment';

import {AuthServiceConfig, FacebookLoginProvider, SocialLoginModule} from "angular-6-social-login-v2";
import {SigninComponent} from './signin/signin.component';
import {BsDropdownModule, ModalModule, TooltipModule} from "ngx-bootstrap";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {CookieService} from "ngx-cookie-service";

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
    FontAwesomeModule
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
}
