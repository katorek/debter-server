export class Debt {
  debtor: string;
  creditor: string;
  amount: number;

  constructor() {
  }

  /*constructor(debtor: string, creditor: string, amount: number) {
    this.debtor = debtor;
    this.creditor = creditor;
    this.amount = amount;
  }*/

  toString(): string {
    return 'Dług {kto dłużny: \'' + this.debtor +
      '\', komu dłużny: \'' + this.creditor +
      '\', ile dłużny: ' + this.amount + '}';
  }
}
