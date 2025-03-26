import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { noFutureDateValidator, noYoungerThan21 } from '../../models/validators/NoFutureDateValidator';
import { User } from '../../models/models';
import { AddService } from '../../service/add.service';
import { UpdateStoreService } from '../../store/updatestore.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: false,
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit{
  private fb = inject(FormBuilder)
  private addSvc = inject(AddService)
  private updateStore = inject(UpdateStoreService)
  private router = inject(Router)

  protected form !:FormGroup

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm():FormGroup{
    return this.fb.group({
      firstName : this.fb.control<string>('', [Validators.required, Validators.min(2)]),
      lastName : this.fb.control<string>('',[Validators.required, Validators.min(3)]),
      email : this.fb.control<string>('',[Validators.required, Validators.email]),
      password : this.fb.control<string>('',[Validators.required, Validators.min(7)]),
      dateOfBirth : this.fb.control<string>('',[Validators.required, noYoungerThan21()])
    })
  }

  signUp():void {
    const user:User = {
      firstName : this.form.value.firstName,
      lastName : this.form.value.lastName,
      email : this.form.value.email,
      password : this.form.value.password,
      dateOfBirth : this.form.value.dateOfBirth
    }
    this.addSvc.createUser(user).then((response) => {
      this.updateStore.updateEmailAndToken(user.email, response.token)
      this.router.navigate(['/'])
    })
  }
  
}
