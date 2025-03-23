import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { ExpenselistComponent } from './components/expenselist/expenselist.component';
import { AddExpenseComponent } from './components/add-expense/add-expense.component';
import { ExpensedashboardComponent } from './components/expensedashboard/expensedashboard.component';

const routes: Routes = [
  {path:"",component:LoginComponent},
  {path:"home",component:HomeComponent},
  {path:"expenses",component:ExpenselistComponent},
  {path:"expenses/addexpense",component:AddExpenseComponent},
  {path:"expenses/dashboard",component:ExpensedashboardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
