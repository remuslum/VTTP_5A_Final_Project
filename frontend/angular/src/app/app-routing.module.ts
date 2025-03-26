import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { ExpenselistComponent } from './components/expenselist/expenselist.component';
import { AddItemsComponent } from './components/add-items/add-items.component';
import { LoanCalculatorComponent } from './components/loan-calculator/loan-calculator.component';
import { checkIfAuthenticated } from './models/validators/router.validator';
import { LoanpaymentslistComponent } from './components/loanpaymentslist/loanpaymentslist.component';
import { UpdateexpenseComponent } from './components/updateexpense/updateexpense.component';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
  {path:"",component:LoginComponent},
  {path:"signup",component:SignupComponent},
  {path:"home",component:HomeComponent, canActivate : [checkIfAuthenticated]},
  {path:"expenses",component:ExpenselistComponent, canActivate: [checkIfAuthenticated]},
  {path:"loanpayment",component:LoanpaymentslistComponent, canActivate: [checkIfAuthenticated]},
  {path:"add",component:AddItemsComponent, canActivate: [checkIfAuthenticated]},
  {path:"calculator",component:LoanCalculatorComponent, canActivate: [checkIfAuthenticated]},
  {path:"updateexpense",component:UpdateexpenseComponent, canActivate:[checkIfAuthenticated]},
  {path:"**",redirectTo:"/",pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
