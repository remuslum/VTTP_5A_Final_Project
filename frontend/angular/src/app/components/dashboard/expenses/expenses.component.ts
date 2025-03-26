import { Component, inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CurrentSpending } from '../../../models/models';
import { GetService } from '../../../service/get.service';
import { QueryStoreService } from '../../../store/querystore.service';

@Component({
  selector: 'app-expenses',
  standalone: false,
  templateUrl: './expenses.component.html',
  styleUrl: './expenses.component.css'
})
export class ExpensesComponent implements OnInit{
  private getSvc = inject(GetService)
  private store = inject(QueryStoreService)

  protected currentSpending$!: Observable<CurrentSpending>
  protected currentSpending:number = 0
  protected maxSpending:number = 0
  protected maxCategory:string = ''
  protected email:string = ''


  ngOnInit(): void {
    this.store.email$.subscribe({
      next: data => this.email = data
    })

    this.currentSpending$ = this.getSvc.getCurrentSpending(this.email)
    this.currentSpending$.subscribe({
      next : data => this.currentSpending = data.amount
    })

    this.getSvc.getMaxSpending(this.email).subscribe({
      next: data => {
        this.maxCategory = data.category
        this.maxSpending = data.spending
      }
    })
  }

}
