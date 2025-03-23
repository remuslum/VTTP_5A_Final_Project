import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './components/home/home.component';
import { provideHttpClient } from '@angular/common/http';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireAuthModule } from '@angular/fire/compat/auth';
import { firebaseConfig } from "./enviroments/environment";
import { SyncgridComponent } from './components/syncgrid/syncgrid.component';
import { GridAllModule } from '@syncfusion/ej2-angular-grids';
import { NgbModule, NgbNavModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { AddExpenseComponent } from './components/add-expense/add-expense.component';
import { ExpensedashboardComponent } from './components/expensedashboard/expensedashboard.component';
import { ExpenselistComponent } from './components/expenselist/expenselist.component';
import { AddloanpaymentComponent } from './components/addloanpayment/addloanpayment.component';
import { AddItemsComponent } from './components/add-items/add-items.component'

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SyncgridComponent,
    AddExpenseComponent,
    ExpensedashboardComponent,
    ExpenselistComponent,
    AddloanpaymentComponent,
    AddItemsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    AngularFireModule.initializeApp(firebaseConfig),
    AngularFireAuthModule,
    GridAllModule,
    NgbModule,
    NgbTooltipModule,
    NgbNavModule
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
