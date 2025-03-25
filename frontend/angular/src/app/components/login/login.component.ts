import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { GooglesigninService } from '../../service/googlesignin.service';
import { Router } from '@angular/router';
import { UpdateStoreService } from '../../store/updatestore.service';
import { trigger, transition, animate, keyframes, style } from '@angular/animations';
import { LoginService } from '../../service/login.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserDetails } from '../../models/models';

@Component({
  selector: 'app-login',
  animations: [
    trigger('typewriter', [
      transition(':increment', [
        animate('0.1s', keyframes([
          style({ opacity: 0.5, offset: 0 }),
          style({ opacity: 1, offset: 1.0 })
        ]))
      ])
    ])],
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit, OnDestroy{
  private googleSignInSvc = inject(GooglesigninService)
  private router = inject(Router)
  private updateStore = inject(UpdateStoreService)
  private loginSvc = inject(LoginService)
  private fb = inject(FormBuilder)

  protected fullText = "Start tracking your financial goals with us today";
  protected displayedText = "";
  protected form!: FormGroup
  private typingInterval: any;

  ngOnInit():void {
    this.typeText();
    this.form = this.createForm()
  }

  ngOnDestroy():void {
    clearInterval(this.typingInterval);
  }

  createForm():FormGroup{
    return this.fb.group({
      email : this.fb.control<string>('', [Validators.required]),
      password : this.fb.control<string>('', [Validators.required])
    })
  }

  typeText() {
    let i = 0;
    this.typingInterval = setInterval(() => {
      if (i < this.fullText.length) {
        this.displayedText += this.fullText.charAt(i);
        i++;
      } else {
        clearInterval(this.typingInterval);
      }
    }, 100);
  }

  signInWithGoogle(){
    this.googleSignInSvc.signInWithGoogle().then(async (user) => {
      const token = await user.getIdToken();
      this.updateStore.updateEmailAndToken(user.email, token);
      this.router.navigate(["/home"]);
    }).catch(error => {
      console.error("Google sign-in failed:", error);
      // Add appropriate error handling here
    });
  }

  signInWithEmailAndPassword(){
    const userDetails:UserDetails = {
      email: this.form.value.email,
      password: this.form.value.password
    }

    this.loginSvc.getLoginToken(userDetails).then((response) => {
      console.log(response)
      this.updateStore.updateEmailAndToken(userDetails.email, response.token)
      this.router.navigate(['/home'])
    }).catch((error) => {
      
    })
  }
}
