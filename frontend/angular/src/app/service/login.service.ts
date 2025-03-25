import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UserDetails } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private http = inject(HttpClient)

  getLoginToken(userDetails:UserDetails):Promise<any>{
    return lastValueFrom(this.http.post<UserDetails>("/api/login",userDetails))
  }
}
