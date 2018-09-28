import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {DebtsComponent} from './debts/debts.component';
import {HomeComponent} from './home/home.component';
import {environment} from '../environments/environment';

import {AuthServiceConfig, FacebookLoginProvider, SocialLoginModule} from 'angular-6-social-login-v2';
import {BsDropdownModule, ModalModule, TooltipModule} from 'ngx-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {CookieService} from 'ngx-cookie-service';
import {FormsModule} from '@angular/forms';
import 'rxjs/add/operator/finally';
import {LoginComponent} from './login/login.component';
import {NavbarComponent} from './navbar/navbar.component';

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

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    DebtsComponent,
    HomeComponent,
    LoginComponent,
    NavbarComponent
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
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    {
      provide: AuthServiceConfig,
      useFactory: getAuthServiceConfigs
    },
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor() {
  }


}


