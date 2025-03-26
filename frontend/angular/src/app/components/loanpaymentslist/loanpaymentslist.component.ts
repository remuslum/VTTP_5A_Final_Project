import { Component, inject } from '@angular/core';
import { GetService } from '../../service/get.service';
import { QueryStoreService } from '../../store/querystore.service';
import { LatestLoanPayment, LoanPaymentTableItem } from '../../models/models';

@Component({
  selector: 'app-loanpaymentslist',
  standalone: false,
  templateUrl: './loanpaymentslist.component.html',
  styleUrl: './loanpaymentslist.component.css'
})
export class LoanpaymentslistComponent {
  private getSvc = inject(GetService)
  private queryStore = inject(QueryStoreService)
  private email:string = ''

  protected loanPayments:LoanPaymentTableItem[] = []

  protected page = 1;
	protected pageSize = 5;
	protected collectionSize = 0;
	protected loanPaymentsSlice: LoanPaymentTableItem[] = [];
  
  ngOnInit(): void {
    this.queryStore.email$.subscribe({
      next : data => this.email = data
    })
    this.getSvc.getLoanPayments(this.email).subscribe({
      next: data => {
        this.loanPayments = data
        this.collectionSize = this.loanPayments.length
        this.refreshExpenses()
      }
    })
  }

  refreshExpenses() {
    this.loanPaymentsSlice = this.loanPayments.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize
    );
  }
}
