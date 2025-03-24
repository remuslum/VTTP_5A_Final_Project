import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Duration, MonthlyPayment } from '../models/models';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanCalculatorService {
  private http = inject(HttpClient)

  getLoanDuration(monthlyPayment:MonthlyPayment):Promise<any>{
    return lastValueFrom(this.http.post<any>("/api/loan/duration",monthlyPayment))
  }

  getMonthlyPayment(duration:Duration):Promise<any>{
    return lastValueFrom(this.http.post<any>("/api/loan/amount",duration))
  }
}
