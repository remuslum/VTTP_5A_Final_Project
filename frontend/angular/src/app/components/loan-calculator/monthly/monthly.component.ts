import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Duration } from '../../../models/models';
import { LoanCalculatorService } from '../../../service/loan-calculator.service';
import { BehaviorSubject } from 'rxjs';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-monthly',
  standalone: false,
  templateUrl: './monthly.component.html',
  styleUrl: './monthly.component.css',
  animations: [
    trigger('fadeIn', [
      transition('* => *', [ 
        style({ opacity: 0 }),
        animate('400ms ease-in', style({ opacity: 1 }))
      ])
    ])
  ]
})
export class MonthlyComponent {
  private fb = inject(FormBuilder)
  private loanSvc = inject(LoanCalculatorService)

  protected monthlyPayment:BehaviorSubject<number> = new BehaviorSubject<number>(0)
  protected validSubmission:boolean = true
  protected form !: FormGroup

  ngOnInit(): void {
    this.form = this.createForm()
  }

  private createForm():FormGroup{
    return this.fb.group({
      amount : this.fb.control<number>(0, [Validators.required, Validators.min(100)]),
      interestRate : this.fb.control<number>(0, [Validators.required, Validators.min(0), Validators.max(100)]),
      duration : this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
      frequency : this.fb.control<string>('',[Validators.required])
    })
  }

  protected submitForm(){
    if(this.form.valid){
      const value = this.form.value
      const duration:Duration = {
        amount : value.amount,
        interestRate : value.interestRate,
        duration : value.duration,
        frequency : value.frequency
      }
      console.log(duration)
      this.loanSvc.getMonthlyPayment(duration).then((response) => {
        this.monthlyPayment.next(response.payment)
      }).catch((error) => {console.log(error)})
    } else {
      this.validSubmission = !this.validSubmission
    }
    
  }
}
