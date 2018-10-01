import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthenticationService} from "../_services/authentication.service";
import {HttpClient} from "@angular/common/http";
import {Debt} from "../_models/debt";
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {DebtService} from "../_services/debt.service";
import {positiveNumberValidator} from "../_directives/positive-number.directive";
import {first} from "rxjs/operators";
import {AlertService} from "../_services/alert.service";

@Component({
  selector: 'app-debts',
  templateUrl: './debts.component.html',
  styleUrls: ['./debts.component.css']
})
export class DebtsComponent implements OnInit {
  @ViewChild('debtGrid') debtGrid: jqxGridComponent;
  model = new Debt();
  debtForm: FormGroup;
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

  constructor(private auth: AuthenticationService,
              private http: HttpClient,
              private fb: FormBuilder,
              private debtService: DebtService,
              private alertService: AlertService) {
  }

  get f() {
    return this.debtForm.controls;
  }


  newDebt() {
    this.model = new Debt();
  }

  ngOnInit() {
    this.getUserDebts();
    this.debtForm = this.fb.group({
        debtor: ['', Validators.required],
        creditor: ['', Validators.required],
        amount: ['', [Validators.required, positiveNumberValidator()]]
      }, {validator: sameCreditorAndDebtor}
    );
  }

  authenticated() {
    return this.auth.authenticated;
  }

  refresh(data) {
    if (data != null) this.source.localdata = data;
    this.dataAdapter = new jqx.dataAdapter(this.source);
  }

  getUserDebts() {
    this.debtService.getDebts().subscribe(
      (response) => {
        this.refresh(response)
      }, (err) => {
        this.alertService.error(err);
      }
    );
  }

  optimizeDebts() {
    this.debtService.optimizeDebts().subscribe(
      (response) => {
        this.refresh(response);
      }
    )
  }

  onSubmit() {
    if (this.debtForm.invalid) {
      return
    }

    this.debtService.postDebt(this.debtForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Rejestracja udana', true);
          this.source.localdata.push(this.debtForm.value);
          this.refresh(null);
          this.debtForm.reset();
        }, error => {
          this.alertService.error(error);

        }
      )

  }

}

export const sameCreditorAndDebtor: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const creditor = control.get('creditor');
  const debtor = control.get('debtor');
  if (creditor.value === '' || debtor.value === '') return null;
  return debtor && creditor && debtor.value === creditor.value ? {sameCreditorAndDebtor: true} : null;
};
