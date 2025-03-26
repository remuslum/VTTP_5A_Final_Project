import { Component, inject, OnInit } from '@angular/core';
import { Expense } from '../../models/models';
import { GetService } from '../../service/get.service';
import { QueryStoreService } from '../../store/querystore.service';

@Component({
  selector: 'app-expenselist',
  standalone: false,
  templateUrl: './expenselist.component.html',
  styleUrl: './expenselist.component.css'
})
export class ExpenselistComponent implements OnInit{
  private getSvc = inject(GetService)
  private queryStore = inject(QueryStoreService)
  private email:string = ''

  protected expenses:Expense[] = []

  page = 1;
	pageSize = 20;
	collectionSize = 0;
	expensesSlice: Expense[] = [];
  
  ngOnInit(): void {
    this.queryStore.email$.subscribe({
      next : data => this.email = data
    })
    this.getSvc.getExpenseList(this.email).subscribe({
      next: data => {
        this.expenses = data
        this.collectionSize = this.expenses.length
        this.refreshExpenses()
      }
    })
  }

  refreshExpenses() {
    this.expensesSlice = this.expenses.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize
    );
  }

  
  
}
