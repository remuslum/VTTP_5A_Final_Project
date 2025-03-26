import { Component, inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddService } from '../../service/add.service';
import { Loan } from '../../models/models';
import { QueryStoreService } from '../../store/querystore.service';

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

  protected form !: FormGroup
  protected payments !: FormArray
  protected email:string = ''
  

  ngOnInit(): void {
    this.form = this.createForm()
    this.queryStore.email$.subscribe({
      next: data => this.email = data
    })
  }

  private createForm():FormGroup{
    this.payments = this.fb.array([])
    return this.fb.group({
      paymentsList : this.payments
    })
  }

  protected addPayment():void{
    const col = this.fb.group({
      amount : this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
      description : this.fb.control<string>('',[Validators.required,Validators.min(5)])
    })
    this.payments.push(col)
  }

  protected submitForm():void{
    const loans:Loan[] = this.form.value.paymentsList.map((element:any) => ({
      amount: element.amount,
      description: element.description,
      email: this.email
    }))

    this.addSvc.addLoans(loans).then((response) => console.log(response))
  }
}
