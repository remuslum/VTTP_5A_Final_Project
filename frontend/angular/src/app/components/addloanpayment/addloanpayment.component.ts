import { Component, inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { noFutureDateValidator } from '../../models/validators/NoFutureDateValidator';
import { AddService } from '../../service/add.service';
import { Loan } from '../../models/models';

@Component({
  selector: 'app-addloanpayment',
  standalone: false,
  templateUrl: './addloanpayment.component.html',
  styleUrl: './addloanpayment.component.css'
})
export class AddloanpaymentComponent implements OnInit{
  private fb = inject(FormBuilder)
  private addSvc = inject(AddService)

  protected form !: FormGroup
  protected payments !: FormArray

  ngOnInit(): void {
    this.form = this.createForm()
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
      email: "remus@abc.com"
    }))

    this.addSvc.addLoans(loans).then((response) => console.log(response))
  }
}
