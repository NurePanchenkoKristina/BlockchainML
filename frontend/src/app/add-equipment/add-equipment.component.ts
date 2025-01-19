import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-equipment',
  templateUrl: './add-equipment.component.html',
  styleUrls: ['./add-equipment.component.css']
})
export class AddEquipmentComponent {
  equipmentTypeList: any = [];
  selectedType: any = null;
  serialNumber: any = null;

  constructor (private http: HttpClient, private router: Router) {
      this.refreshEquipmentList();
  }

  refreshEquipmentList() {
    this.http.get<any[]>("http://localhost:8080/data/get-equipment-types")
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
        this.equipmentTypeList = response.map(item => {
          return {
            id: item.id,
            name: item.typeName,
            costPerMinute: item.costPerMinute,
            maintenanceCost: item.maintenanceCost
          };
        });
    }
  });
  }

  save() {
    const transaction: any = {
      equipmentTypeId: this.selectedType,
      serialNumber: this.serialNumber,
  };

  this.http.post<any>('http://localhost:8080/data/add-equipment', transaction)
  .pipe(timeout(5000))
  .subscribe({
    next: (response) => {
    this.router.navigate(['/equipment'])
  }
})
  }
}
