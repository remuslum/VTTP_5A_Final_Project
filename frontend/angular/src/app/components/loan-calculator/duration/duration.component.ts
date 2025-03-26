import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MonthlyPayment } from '../../../models/models';
import { LoanCalculatorService } from '../../../service/loan-calculator.service';
import { trigger, transition, style, animate } from '@angular/animations';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-duration',
  standalone: false,
  templateUrl: './duration.component.html',
  styleUrl: './duration.component.css',
  animations: [
    trigger('fadeIn', [
      transition('* => *', [ 
        style({ opacity: 0 }),
        animate('400ms ease-in', style({ opacity: 1 }))
      ])
    ])
  ]
})
export class DurationComponent {
  private fb = inject(FormBuilder)
  private loanSvc = inject(LoanCalculatorService)
  protected duration:BehaviorSubject<string> = new BehaviorSubject<string>('')
  protected validSubmission:boolean = true

  protected form !: FormGroup

  ngOnInit(): void {
    this.form = this.createForm()
  }

  private createForm():FormGroup{
    return this.fb.group({
      amount : this.fb.control<number>(0, [Validators.required, Validators.min(100)]),
      interestRate : this.fb.control<number>(0, [Validators.required, Validators.min(0), Validators.max(100)]),
      payment : this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
      frequency : this.fb.control<string>('',[Validators.required])
    })
  }

  protected submitForm():void{
    if(this.form.valid){
      const value = this.form.value
      const monthlyPayment:MonthlyPayment = {
        amount : value.amount,
        interestRate : value.interestRate,
        payment : value.payment,
        frequency : value.frequency
      }
      this.loanSvc.getLoanDuration(monthlyPayment).then((response) => {
        this.duration.next(response.date_of_last_repayment)
      }).catch((error) => console.log(error))
    } else {
      this.validSubmission = !this.validSubmission
    }
  }
}
