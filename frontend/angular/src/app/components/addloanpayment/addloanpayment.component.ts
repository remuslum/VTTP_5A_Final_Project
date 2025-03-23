import { Component, inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { noFutureDateValidator } from '../../models/validators/NoFutureDateValidator';

@Component({
  selector: 'app-addloanpayment',
  standalone: false,
  templateUrl: './addloanpayment.component.html',
  styleUrl: './addloanpayment.component.css'
})
export class AddloanpaymentComponent implements OnInit{
  private fb = inject(FormBuilder)

  protected form !: FormGroup
  protected payments !: FormArray

  ngOnInit(): void {
    this.form = this.createForm()
  }

  private createForm(){
    this.payments = this.fb.array([])
    return this.fb.group({
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
    console.log(this.form.value)
  }
}
