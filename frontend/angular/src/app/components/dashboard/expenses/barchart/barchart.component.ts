import { Component, inject, OnInit } from '@angular/core';
import { CategorySpending } from '../../../../models/models';
import { GetService } from '../../../../service/get.service';
import { QueryStoreService } from '../../../../store/querystore.service';

@Component({
  selector: 'app-barchart',
  standalone: false,
  templateUrl: './barchart.component.html',
  styleUrl: './barchart.component.css'
})
export class BarchartComponent implements OnInit{
  private getSvc = inject(GetService)
  private store = inject(QueryStoreService)

  protected primaryXAxis?: Object
  protected chartData?: Object[]
  protected categoryData:CategorySpending[] = []
  protected title:string = ''
  protected categories:string[] = ['wants','needs','shopping','entertainment','utilities']
  protected email:string = ''

  ngOnInit(): void {
    this.store.email$.subscribe({
      next: data => this.email = data
    })
    this.getSvc.getSpendingByCategory(this.email).subscribe({
      next: data => {
        this.categoryData = data
      }
    })
    this.chartData = [
      { month: 'Jan', shopping: 35, entertainment: 28, utilities: 40, needs: 60, wants:10 }, 
      { month: 'Feb', shopping: 35, entertainment: 28, utilities: 40, needs: 60, wants:10 },
      { month: 'Mar', shopping: 35, entertainment: 28, utilities: 40, needs: 60, wants:10 }, 
      { month: 'Apr', shopping: 35, entertainment: 28, utilities: 40, needs: 60, wants:10 },
      { month: 'May', shopping: 35, entertainment: 28, utilities: 40, needs: 60, wants:10 }, 
      { month: 'Jun', shopping: 35, entertainment: 28, utilities: 40, needs: 60, wants:10 }
    ];
    this.primaryXAxis = {
        valueType: 'Category'
    };
    this.title = 'Total Spending by Category for the past 3 months'
  }

  
}
