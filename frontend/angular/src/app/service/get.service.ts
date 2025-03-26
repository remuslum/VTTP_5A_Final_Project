import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CategorySpending, CurrentSpending, Expense, LatestLoanPayment, Loan, LoanPaymentTableItem, MaxSpending, MonthSpending, SumOfLoanPayments } from '../models/models';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class GetService {
  private http = inject(HttpClient)
  private headerSvc = inject(HeaderService)


  getCurrentSpending(email:string):Observable<CurrentSpending>{
    const params = new HttpParams().set("email",email)
    const headers = this.headerSvc.getHeaders()
    return this.http.get<CurrentSpending>("/api/get/currentspending",{params:params, headers:headers})
  }

  getSpendingByCategory(email:string):Observable<CategorySpending[]>{
    const params = new HttpParams().set("email",email)
    const headers = this.headerSvc.getHeaders()
    return this.http.get<CategorySpending[]>("api/get/spending/category",{params:params, headers:headers})
  }

  getSpendingByMonth(email:string):Observable<MonthSpending[]>{
    const params = new HttpParams().set("email",email)
    const headers = this.headerSvc.getHeaders()
    return this.http.get<MonthSpending[]>("/api/get/spending/month",{params:params, headers:headers})
  }

  getMaxSpending(email:string):Observable<MaxSpending>{
    const params = new HttpParams().set("email",email)
    const headers = this.headerSvc.getHeaders()
    return this.http.get<MaxSpending>("/api/get/maxspending",{params:params, headers:headers})
  }

  getExpenseList(email:string):Observable<Expense[]>{
    const params = new HttpParams().set("email",email)
    const headers = this.headerSvc.getHeaders()
    return this.http.get<Expense[]>("/api/get/expenses",{params:params, headers:headers}) 
  }

  getAllLoans(email:string):Observable<Loan[]>{
    const headers = this.headerSvc.getHeaders()
    const params = new HttpParams().set("email",email)
    return this.http.get<Loan[]>("/api/get/loans", {params:params, headers:headers})
  }

  getSumOfLoanPayments(email:string):Observable<SumOfLoanPayments[]>{
    const headers = this.headerSvc.getHeaders()
    const params = new HttpParams().set("email",email)
    return this.http.get<SumOfLoanPayments[]>("/api/get/sum/loanpayments", {params:params, headers:headers}) 
  }

  getLatestLoanPayment(email:string):Observable<LatestLoanPayment>{
    const headers = this.headerSvc.getHeaders()
    const params = new HttpParams().set("email",email)
    return this.http.get<LatestLoanPayment>("/api/get/loanpayment/latest", {params:params, headers:headers})  
  }

  getLoanPayments(email:string):Observable<LoanPaymentTableItem[]>{
    const headers = this.headerSvc.getHeaders()
    const params = new HttpParams().set("email",email)
    return this.http.get<LoanPaymentTableItem[]>("/api/get/loanpayments", {params:params, headers:headers})  
  }
}
