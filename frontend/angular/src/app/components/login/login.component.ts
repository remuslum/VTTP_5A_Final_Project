import { Component, inject } from '@angular/core';
import { GooglesigninService } from '../../service/googlesignin.service';
import { Router } from '@angular/router';
import { UpdateStoreService } from '../../store/updatestore.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{
  private googleSignInSvc = inject(GooglesigninService)
  private router = inject(Router)
  private updateStore = inject(UpdateStoreService)

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
}
