import { Injectable } from '@angular/core';
import { Store, StoreConfig } from '@datorama/akita';

export interface SessionState {
    email : string
    token : string
}

export function createInitialState():SessionState{
    return {
        email: '',
        token:''
    }
}

@Injectable({
    providedIn: 'root'
})
@StoreConfig({name:'session'})
export class SessionStore extends Store<SessionState>{
    constructor(){
        super(createInitialState())
    }
}