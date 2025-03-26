import { Component, inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddService } from '../../service/add.service';
import { Loan, LoanPayment } from '../../models/models';
import { QueryStoreService } from '../../store/querystore.service';
import { GetService } from '../../service/get.service';
import { noFutureDateValidator } from '../../models/validators/NoFutureDateValidator';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addloanpayment',
  standalone: false,
  templateUrl: './addloanpayment.component.html',
  styleUrl: './addloanpayment.component.css'
})
export class AddloanpaymentComponent implements OnInit{
  private fb = inject(FormBuilder)
  private addSvc = inject(AddService)
  private queryStore = inject(QueryStoreService)
  private getSvc = inject(GetService)
  private router = inject(Router)

  protected form !: FormGroup
  protected payments !: FormArray
  protected email:string = ''
  protected loans:Loan[] = []
  

  ngOnInit(): void {
    this.form = this.createForm()
    this.queryStore.email$.subscribe({
      next: data => this.email = data
    })
    this.getSvc.getAllLoans(this.email).subscribe({
      next: data => this.loans = data
    })
  }

  private createForm():FormGroup{
    this.payments = this.fb.array([])
    return this.fb.group({
      id: this.fb.control<string>('',[Validators.required]),
      paymentsList : this.payments
    })
  }

  protected addPayment():void{
    const col = this.fb.group({
      amount : this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
      date : this.fb.control<string>('',[Validators.required,noFutureDateValidator()])
    })
    this.payments.push(col)
  }

  protected submitForm():void{
    const loanPayments:LoanPayment[] = this.form.value.paymentsList.map((element:any) => ({
      loan_id: this.form.value.id,
      date: element.date,
      amount: element.amount
    }))
    this.addSvc.addLoanPayments(loanPayments).then((response) => {
      this.router.navigate(['/home'])
    })

    // this.addSvc.addLoans(loanPayments).then((response) => console.log(response))
  }
}
