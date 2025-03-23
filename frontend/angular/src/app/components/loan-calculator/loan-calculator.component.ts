import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-loan-calculator',
  standalone: false,
  templateUrl: './loan-calculator.component.html',
  styleUrl: './loan-calculator.component.css'
})
export class LoanCalculatorComponent {
  
  protected active:number = 1
      
}
