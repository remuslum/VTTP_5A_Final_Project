import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-monthly',
  standalone: false,
  templateUrl: './monthly.component.html',
  styleUrl: './monthly.component.css'
})
export class MonthlyComponent {
  private fb = inject(FormBuilder)

  protected form !: FormGroup

  ngOnInit(): void {
    this.form = this.createForm()
  }

  private createForm():FormGroup{
    return this.fb.group({
      amount : this.fb.control<number>(0, [Validators.required, Validators.min(0)]),
      interestRate : this.fb.control<number>(0, [Validators.required, Validators.min(0), Validators.max(100)]),
      duration : this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
      frequency : this.fb.control<string>('',[Validators.required])
    })
  }
}
