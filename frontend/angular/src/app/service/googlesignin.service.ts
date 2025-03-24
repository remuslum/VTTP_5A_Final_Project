import { inject, Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import firebase from 'firebase/compat/app';

@Injectable({
  providedIn: 'root'
})
export class GooglesigninService {

  private afAuth = inject(AngularFireAuth)
  // Google Sign-In
  async signInWithGoogle():Promise<any> {
    try {
      const result = await this.afAuth.signInWithPopup(new firebase.auth.GoogleAuthProvider());
      const user = result.user
      return user
    } catch (error) {
      console.error('Google Sign-In Error:', error);
      return null;
    }
  }

  // Logout
  async logout() {
    await this.afAuth.signOut();
  }

  // Get Auth State
  getAuthState() {
    return this.afAuth.authState;
  }

}
