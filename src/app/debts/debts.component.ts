import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {AuthenticationService} from "../_services/authentication.service";
import {HttpClient} from "@angular/common/http";
import {Debt} from "../_models/debt";
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";

@Component({
  selector: 'app-debts',
  templateUrl: './debts.component.html',
  styleUrls: ['./debts.component.css']
})
export class DebtsComponent implements OnInit, AfterViewInit {
  @ViewChild('debtGrid') debtGrid: jqxGridComponent;
  model = new Debt();
  errorMessage = '';
  doFade = false;
  showError = false;
  source: any = {
    datafields: [{name: 'debtor'}, {name: 'creditor'}, {name: 'amount'}],
    datatype: 'json'
  };

  columns: any[] = [
    {text: 'Dłużnik', datafield: 'debtor', width: 200},
    {text: 'Kredytor', datafield: 'creditor', width: 200},
    {text: 'Ilość (zł)', datafield: 'amount', width: 100},
  ];
  // START
  dataAdapter: any = new jqx.dataAdapter(this.source);

  constructor(private auth: AuthenticationService, private http: HttpClient) {
  }

  ngOnInit() {
    this.getUserDebts();
  }

  ngAfterViewInit(): void {
    /*var localizationobj: jqwidgets.GridLocalizationobject = {};
    localizationobj.pagergotopagestring = "Idź do";
    localizationobj.pagershowrowsstring = "Zeige Zeile:";
    localizationobj.pagerrangestring = " ?? ";
    localizationobj.pagernextbuttonstring = " ?? ";
    localizationobj.sortascendingstring = "Sortuj rosnąco";
    localizationobj.sortdescendingstring = "Sortuj malejąco";
    localizationobj.sortremovestring = "Wyczyść sortowanie";
    this.debtGrid.autoheight(true);
    this.debtGrid.localizestrings(localizationobj);*/
  }

  newDebt() {
    this.model = new Debt();
  }

  authenticated() {
    return this.auth.authenticated;
  }

  refresh(data) {
    if (data != null) this.source.localdata = data;
    this.dataAdapter = new jqx.dataAdapter(this.source);
  }

  getUserDebts() {
    const user = this.auth.getLoggedUser();
    this.http.get<Array<Debt>>('/debts').subscribe(
      (response) => {
        this.refresh(response)
      }, (err) => {
        this.handleError('Error', err, 2000);
      }
    )
  }

  optimizeDebts() {
    this.http.get('/debts/optimize').subscribe(
      (response) => {
        this.refresh(response);
      }
    )
  }

  onSubmit() {
    if (this.model.creditor == this.model.debtor) {
      this.handleError('Dłużnik nie może być równocześnie kredytobiorcą', '', 2500);
    }
    else {
      this.http.post('/debts', this.model).subscribe(
        (response) => {
          this.source.localdata.push(this.model);
          this.refresh(null);
          this.newDebt();
        }, (err) => {
          console.log(err);
          this.newDebt();
          this.handleError('Nie udało się dodać długu. Po więcej informacji sprawdź konsolę', err, 2500);
        }
      )
    }
  }

  handleError(errMsg, error, timeout) {
    console.log(error);
    this.showError = false;
    this.doFade = false;
    this.showError = true;
    this.errorMessage = errMsg;
    setTimeout(() => {
      this.doFade = true;
    }, timeout);
  }

//  END
}
