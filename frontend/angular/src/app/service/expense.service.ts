import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Expense } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private expenseSubject:BehaviorSubject<Expense> = new BehaviorSubject<Expense>(this.createEmptyExpense())

  addExpense(expense:Expense){
    this.expenseSubject.next(expense)
  }

  getExpense():Expense{
    return this.expenseSubject.getValue()
  }

  private createEmptyExpense(): Expense {
    return {
      id: 0,
      name:'',
      amount: 0,
      description: '',
      category: '',
      date: '',
      email:''
    };
  }
}
