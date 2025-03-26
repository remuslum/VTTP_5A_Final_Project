import { Component, inject, OnInit } from '@angular/core';
import { GetService } from '../../../service/get.service';
import { QueryStoreService } from '../../../store/querystore.service';
import { LatestLoanPayment, Loan, SumOfLoanPayments } from '../../../models/models';

@Component({
  selector: 'app-loans',
  standalone: false,
  templateUrl: './loans.component.html',
  styleUrl: './loans.component.css'
})
export class LoansComponent implements OnInit{
  private getSvc = inject(GetService)
  private queryStore = inject(QueryStoreService)

  protected loans:Loan[] = []
  protected loanPayments:SumOfLoanPayments[] = []
  protected email:string = ''
  protected latestLoanPayment:LatestLoanPayment = {
    amount:0,
    description:'',
    date:''
  }

  ngOnInit(): void {
    this.queryStore.email$.subscribe({
      next : data => this.email = data
    })

    this.getSvc.getAllLoans(this.email).subscribe({
      next: data => this.loans = data
    })

    this.getSvc.getSumOfLoanPayments(this.email).subscribe({
      next: data => this.loanPayments = data
    })

    this.getSvc.getLatestLoanPayment(this.email).subscribe({
      next: data => {
        console.log(data)
        this.latestLoanPayment = data
      }
    })
  }



}
