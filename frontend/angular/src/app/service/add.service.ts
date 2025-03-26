import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Expense, Loan } from '../models/models';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class AddService {
  private http = inject(HttpClient)
  private headerSvc = inject(HeaderService)

  addExpenses(expenses:Expense[]):Promise<any>{
     const headers = this.headerSvc.getHeaders()
    return lastValueFrom(this.http.post<Expense[]>("/api/insert/expenses",expenses,{headers:headers}))
  }

  addLoans(loans:Loan[]):Promise<any>{
     const headers = this.headerSvc.getHeaders()
    return lastValueFrom(this.http.post<Loan[]>("/api/insert/loans",loans,{headers:headers}))
  }

}
