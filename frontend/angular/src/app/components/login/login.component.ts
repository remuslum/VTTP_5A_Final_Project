import { Component, inject, OnInit } from '@angular/core';
import { GooglesigninService } from '../../service/googlesignin.service';
import { Router } from '@angular/router';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import firebase from 'firebase/compat/app';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{
  private googleSignInSvc = inject(GooglesigninService)
  private router = inject(Router)

  signInWithGoogle(){
    this.googleSignInSvc.signInWithGoogle().then((token) => {
      console.log(token)
      this.router.navigate(["/home"])
    })
  }
}
