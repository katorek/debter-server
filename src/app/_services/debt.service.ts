import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Debt} from "../_models/debt";
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class DebtService {

  constructor(private http: HttpClient,
              private auth: AuthenticationService) {
  }

  getDebts() {
    return this.http.get<Array<Debt>>('/debts');
  }

  optimizeDebts() {
    return this.http.get('/debts/optimize');
  }

  postDebt(debt: Debt) {
    return this.http.post("/debts", debt);
  }
}
