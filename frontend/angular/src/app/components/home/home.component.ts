import { Component, inject, OnInit } from '@angular/core';
import { QueryStoreService } from '../../store/querystore.service';
import { Observable } from 'rxjs';
import { AddService } from '../../service/add.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent{
  private addSvc = inject(AddService)
  private router = inject(Router)

  protected active:string = 'expenses'


  logUserOut(){
    this.addSvc.logUserOut().then((response) => {
      this.router.navigate(['/'])
    })
  }

}
