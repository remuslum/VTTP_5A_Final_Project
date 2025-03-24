import { Query } from "@datorama/akita";
import { SessionState, SessionStore } from "./user.store";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class QueryStoreService extends Query<SessionState>{

    constructor(protected override store:SessionStore){ 
        super(store)
        this.initSelectors()
    }

    email$ !: Observable<string>
    token$ !: Observable<string>

    private initSelectors(){
        this.email$ = this.select(state => state.email)
        this.token$ = this.select(state => state.token) 
    }

   
}