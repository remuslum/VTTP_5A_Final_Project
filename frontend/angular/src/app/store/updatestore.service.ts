import { inject, Injectable } from "@angular/core";
import { SessionStore } from "./user.store";

@Injectable({
    providedIn: 'root'
})
export class UpdateStoreService {
    private store = inject(SessionStore)

    updateEmailAndToken(newEmail:string, newToken:string){
        this.store.update(state => {
            return {
                email: newEmail,
                token: newToken
            } 
        })
    }
}