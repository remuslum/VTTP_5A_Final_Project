import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { addLoan, Expense, LoanPayment, User } from '../models/models';
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

  addLoan(loan:addLoan):Promise<any>{
     const headers = this.headerSvc.getHeaders()
    return lastValueFrom(this.http.post<addLoan>("/api/insert/loan",loan,{headers:headers}))
  }

  addLoanPayments(loanPayments:LoanPayment[]):Promise<any>{
    const headers = this.headerSvc.getHeaders()
    return lastValueFrom(this.http.post<LoanPayment[]>("/api/insert/loanpayments", loanPayments, {headers:headers}))
  }

  updateExpense(expense:Expense):Promise<any>{
    const headers = this.headerSvc.getHeaders() 
    return lastValueFrom(this.http.put<Expense>("/api/update/expense", expense, {headers:headers}))
  }

  createUser(user:User):Promise<any>{
    return lastValueFrom(this.http.post<User>("/api/signup",user))
  }

  logUserOut():Promise<any>{
    const headers = this.headerSvc.getHeaders() 
    return lastValueFrom(this.http.post("/api/logout","",{headers:headers}))
  }

}
