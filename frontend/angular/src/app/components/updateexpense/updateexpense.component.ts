import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { noFutureDateValidator } from '../../models/validators/NoFutureDateValidator';
import { ExpenseService } from '../../service/expense.service';
import { Expense } from '../../models/models';
import { AddService } from '../../service/add.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-updateexpense',
  standalone: false,
  templateUrl: './updateexpense.component.html',
  styleUrl: './updateexpense.component.css'
})
export class UpdateexpenseComponent implements OnInit{
  private fb = inject(FormBuilder)
  private expSvc = inject(ExpenseService)
  private addSvc = inject(AddService)
  private router = inject(Router)
  
  protected expense:Expense = {
    id: 0,
    name : '',
    date : '',
    amount : 0,
    category : '',
    description : '',
    email : ''
  }
  protected form !: FormGroup

  ngOnInit(): void {
    this.expense = this.expSvc.getExpense()
    this.form = this.createForm()
  }

  createForm():FormGroup{
    return this.fb.group({
      name : this.fb.control<string>(this.expense.name,[Validators.required, Validators.min(4)]),
      date : this.fb.control<string>(this.expense.date,[Validators.required, noFutureDateValidator()]),
      amount : this.fb.control<number>(this.expense.amount,[Validators.min(0.01)]),
      category : this.fb.control<string>(this.expense.category,[Validators.required]),
      description : this.fb.control<string>(this.expense.description,[Validators.required, Validators.min(10)])
    })
  }

  protected submitForm():void{
    const expenseToUpdate:Expense = {
      id : this.expense.id,
      name : this.form.value.name,
      date : this.form.value.date,
      amount : this.form.value.amount,
      category : this.form.value.category,
      description : this.form.value.description,
      email : this.expense.email
    }

    this.addSvc.updateExpense(expenseToUpdate).then((response) => this.router.navigate(['/home']))
  }

  
}
