import { Component, inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { noFutureDateValidator } from '../../models/validators/NoFutureDateValidator';
import { Expense } from '../../models/models';
import { AddService } from '../../service/add.service';

@Component({
  selector: 'app-add-expense',
  standalone: false,
  templateUrl: './add-expense.component.html',
  styleUrl: './add-expense.component.css'
})
export class AddExpenseComponent implements OnInit{
  private fb = inject(FormBuilder)
  private addSvc = inject(AddService)
  
  protected form !: FormGroup
  protected expenses !: FormArray

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm():FormGroup{
    this.expenses = this.fb.array([])
    return this.fb.group({
      expenseList : this.expenses
    })

  }

  protected createExpense():FormGroup {
    return this.fb.group({
      name : this.fb.control<string>('',[Validators.required, Validators.min(4)]),
      date : this.fb.control<string>('',[Validators.required, noFutureDateValidator()]),
      amount : this.fb.control<number>(0,[Validators.min(0.01)]),
      category : this.fb.control<string>('',[Validators.required]),
      description : this.fb.control<string>('',[Validators.required, Validators.min(10)])
    })
  }

  protected addExpense():void {
    const col = this.createExpense()
    this.expenses.push(col)
  }

  protected submitForm():void{
    const expenses: Expense[] = this.form.value.expenseList.map((element: any) => ({
      name: element.name,
      date: element.date,
      amount: element.amount,
      category: element.category,
      description: element.description,
      email: "remus@abc.com"
    }));
    
    this.addSvc.addExpenses(expenses).then((response) => console.log(response))

  }
}
