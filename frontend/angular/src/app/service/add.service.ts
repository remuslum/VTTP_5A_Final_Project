import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Expense, Loan } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class AddService {
  private http = inject(HttpClient)

  addExpenses(expenses:Expense[]):Promise<any>{
    return lastValueFrom(this.http.post<Expense[]>("/api/insert/expenses",expenses))
  }

  addLoans(loans:Loan[]):Promise<any>{
    return lastValueFrom(this.http.post<Loan[]>("/api/insert/loans",loans))
  }

}
