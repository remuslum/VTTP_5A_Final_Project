import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { addLoan } from '../../models/models';
import { QueryStoreService } from '../../store/querystore.service';
import { AddService } from '../../service/add.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addloan',
  standalone: false,
  templateUrl: './addloan.component.html',
  styleUrl: './addloan.component.css'
})
export class AddloanComponent implements OnInit {
  private fb = inject(FormBuilder)
  private queryStore = inject(QueryStoreService)
  private addSvc = inject(AddService)
  private router = inject(Router)

  protected form !: FormGroup
  protected email:string = ''

  ngOnInit(): void {
    this.form = this.createForm()
    this.queryStore.email$.subscribe({
      next: data => this.email = data
    })
  }

  createForm():FormGroup{
    return this.fb.group({
      amount : this.fb.control<number>(0, [Validators.required, Validators.min(100)]),
      description : this.fb.control<string>('',[Validators.required, Validators.min(5)])
    })
  }

  submitForm():void{
    const loanToAdd:addLoan = {
      amount : this.form.value.amount,
      description : this.form.value.description,
      email : this.email
    }
    this.addSvc.addLoan(loanToAdd).then((response) => {
      this.router.navigate(['/home'])
    })
  }
}
