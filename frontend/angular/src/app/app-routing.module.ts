import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { ExpenselistComponent } from './components/expenselist/expenselist.component';
import { AddExpenseComponent } from './components/add-expense/add-expense.component';
import { ExpensedashboardComponent } from './components/expensedashboard/expensedashboard.component';
import { AddloanpaymentComponent } from './components/addloanpayment/addloanpayment.component';
import { AddItemsComponent } from './components/add-items/add-items.component';
import { LoanCalculatorComponent } from './components/loan-calculator/loan-calculator.component';
import { checkIfAuthenticated } from './models/validators/router.validator';

const routes: Routes = [
  {path:"",component:LoginComponent},
  {path:"home",component:HomeComponent, canActivate : [checkIfAuthenticated]},
  {path:"expenses",component:ExpenselistComponent, canActivate: [checkIfAuthenticated]},
  {path:"add",component:AddItemsComponent, canActivate: [checkIfAuthenticated]},
  {path:"calculator",component:LoanCalculatorComponent, canActivate: [checkIfAuthenticated]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
