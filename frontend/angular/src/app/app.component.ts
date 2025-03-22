import { Component, inject, OnInit } from '@angular/core';
import { FirebaseconfigService } from './service/firebaseconfig.service';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireAuthModule } from '@angular/fire/compat/auth';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent{
  title = 'vttp_5a_final_project';

  private firebaseSvc = inject(FirebaseconfigService)

}
