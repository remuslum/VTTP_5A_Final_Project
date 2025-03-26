import { Component, inject, OnInit } from '@angular/core';
import { GetService } from '../../../../service/get.service';
import { MonthSpending } from '../../../../models/models';
import { QueryStoreService } from '../../../../store/querystore.service';

@Component({
  selector: 'app-linechart',
  standalone: false,
  templateUrl: './linechart.component.html',
  styleUrl: './linechart.component.css'
})
export class LinechartComponent implements OnInit{
  private getSvc = inject(GetService)
  private store = inject(QueryStoreService)

  protected primaryXAxis?:Object
  protected primaryYAxis?:Object
  protected lineData?: Object[]
  protected lineTitle:string =''

  protected spendingData:MonthSpending[] = []
  protected email:string = ''

  ngOnInit(): void {
    this.store.email$.subscribe({
      next: data => this.email = data
    })

    this.getSvc.getSpendingByMonth(this.email).subscribe({
      next: data => this.spendingData = data
    })

    this.lineData = [
      {month: 'Jan', total: 240},
      {month: 'Feb', total: 260},
      {month: 'Mar', total: 220},
      {month: 'Apr', total: 340},
      {month: 'May', total: 480},
      {month: 'Jun', total: 390},
    ]

    this.primaryXAxis = {
      interval : 1,
    }

    this.primaryYAxis = {
      title : 'Spending'
    }

    this.lineTitle = 'Average Spending this year'
  }
}
