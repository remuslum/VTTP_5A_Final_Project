import { Component } from '@angular/core';

@Component({
  selector: 'app-add-items',
  standalone: false,
  templateUrl: './add-items.component.html',
  styleUrl: './add-items.component.css'
})
export class AddItemsComponent {
  protected active:number = 1
}
