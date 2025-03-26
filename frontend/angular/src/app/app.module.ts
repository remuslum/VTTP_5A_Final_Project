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
import { NgbModule, NgbNavModule, NgbPaginationModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { AddExpenseComponent } from './components/add-expense/add-expense.component';
import { ExpensedashboardComponent } from './components/expensedashboard/expensedashboard.component';
import { ExpenselistComponent } from './components/expenselist/expenselist.component';
import { AddloanpaymentComponent } from './components/addloanpayment/addloanpayment.component';
import { AddItemsComponent } from './components/add-items/add-items.component';
import { LoanCalculatorComponent } from './components/loan-calculator/loan-calculator.component';
import { MonthlyComponent } from './components/loan-calculator/monthly/monthly.component';
import { DurationComponent } from './components/loan-calculator/duration/duration.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ExpensesComponent } from './components/dashboard/expenses/expenses.component';
import { LoansComponent } from './components/dashboard/loans/loans.component';
import { ChartModule, ChartAllModule, AccumulationChartModule, AccumulationDataLabelService, AccumulationLegendService, PieSeriesService} from '@syncfusion/ej2-angular-charts';
import { BarchartComponent } from './components/dashboard/expenses/barchart/barchart.component';
import { LinechartComponent } from './components/dashboard/expenses/linechart/linechart.component';

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
    AddItemsComponent,
    LoanCalculatorComponent,
    MonthlyComponent,
    DurationComponent,
    ExpensesComponent,
    LoansComponent,
    BarchartComponent,
    LinechartComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    AngularFireModule.initializeApp(firebaseConfig),
    AngularFireAuthModule,
    GridAllModule,
    NgbModule,
    NgbTooltipModule,
    NgbNavModule,
    ChartModule, 
    ChartAllModule,
    AccumulationChartModule,
    NgbPaginationModule
  ],
  providers: [provideHttpClient(),provideAnimationsAsync(), AccumulationDataLabelService,AccumulationLegendService,PieSeriesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
