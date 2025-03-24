import { Component, inject, OnInit } from '@angular/core';
import { QueryStoreService } from '../../store/querystore.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  private queryStore = inject(QueryStoreService)

  protected token$!:Observable<string>
  protected email$!:Observable<string>

  ngOnInit(): void {
    console.log(this.queryStore.getValue())
    this.token$ = this.queryStore.token$
    this.token$.subscribe({
      next : data => console.log(data)
    })

    this.email$ = this.queryStore.email$
    this.email$.subscribe({
      next : data => console.log(data)
    })
  }

}
