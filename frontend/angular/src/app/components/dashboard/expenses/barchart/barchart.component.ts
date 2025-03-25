import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-barchart',
  standalone: false,
  templateUrl: './barchart.component.html',
  styleUrl: './barchart.component.css'
})
export class BarchartComponent implements OnInit{
  protected primaryXAxis?: Object
  protected chartData?: Object[]
  protected title:string = ''

  ngOnInit(): void {
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
    this.title = 'Total Spending by Category for the past 6 months'
  }

  
}
