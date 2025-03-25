import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-linechart',
  standalone: false,
  templateUrl: './linechart.component.html',
  styleUrl: './linechart.component.css'
})
export class LinechartComponent implements OnInit{
  protected primaryXAxis?:Object
  protected primaryYAxis?:Object
  protected lineData?: Object[]
  protected lineTitle:string =''

  ngOnInit(): void {
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
      valueType : 'Category'
    }

    this.primaryYAxis = {
      title : 'Spending'
    }

    this.lineTitle = 'Total Spending over the last 6 months'
  }
}
