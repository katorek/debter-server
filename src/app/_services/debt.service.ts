import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Debt} from '../_models/debt';
import {AuthenticationService} from './authentication.service';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DebtService {

  constructor(private http: HttpClient,
              private auth: AuthenticationService) {
  }

  getDebts() {
    return this.http.get<Array<Debt>>(`${environment.apiUrl}/debts`);
  }

  optimizeDebts() {
    return this.http.get(`${environment.apiUrl}/debts/optimize`);
  }

  postDebt(debt: Debt) {
    return this.http.post(`${environment.apiUrl}/debts`, debt);
  }
}
