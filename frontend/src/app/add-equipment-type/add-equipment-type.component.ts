import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-equipment-type',
  templateUrl: './add-equipment-type.component.html',
  styleUrls: ['./add-equipment-type.component.css']
})
export class AddEquipmentTypeComponent {
    name: string = "";
    cost: number = 0;
    maintenance_cost: number = 0;

    constructor (private http: HttpClient, private router: Router) {
        
    }

    add() {
      const transaction: any = {
       typeName: this.name,
       costPerMinute: this.cost,
       maintenanceCost: this.maintenance_cost
    };

    this.http.post<any>('http://localhost:8080/data/add-equipment-type', transaction)
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
      this.router.navigate(['/equipment-types'])
    }
  })
}
    }
