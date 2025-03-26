import { inject, Injectable } from '@angular/core';
import { QueryStoreService } from '../store/querystore.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private store = inject(QueryStoreService)
  protected email:string = ''
  protected token:string = ''

  isAuthenticated():boolean{
    this.store.email$.subscribe({
      next : data => this.email = data
    })
    this.store.token$.subscribe({
      next : data => this.token = data
    })

    return (this.email.length != 0) && (this.token.length != 0)
  }
}
