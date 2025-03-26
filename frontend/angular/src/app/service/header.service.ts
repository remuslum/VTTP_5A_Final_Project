import { inject, Injectable } from '@angular/core';
import { QueryStoreService } from '../store/querystore.service';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private queryStore = inject(QueryStoreService)
  protected token:string = ''

  getHeaders():HttpHeaders{
    this.queryStore.token$.subscribe({
      next : data => this.token = data
    })

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
  }
}
