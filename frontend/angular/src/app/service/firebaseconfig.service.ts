import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FirebaseconfigService {
  private http = inject(HttpClient)

  getFirebaseCredentials():Promise<any>{
    return lastValueFrom(this.http.get("/api/firebase/details"))
  }

}
