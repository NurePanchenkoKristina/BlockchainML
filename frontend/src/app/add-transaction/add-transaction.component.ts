import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-transaction',
  templateUrl: './add-transaction.component.html',
  styleUrls: ['./add-transaction.component.css']
})
export class AddTransactionComponent {
    customerList:any = [];
    equipmentList:any = [];

    selectedCustomer: any = null;
    selectedEquipment: any = null;
    startTime: any = null;
    endTime: any = null;

    constructor (private http: HttpClient, private router: Router) {
      this.refreshCustomerList();
      this.refreshEquipmentList();
    }

    refreshCustomerList() {
      this.http.get<any[]>("http://localhost:8080/data/get-customers")
      .pipe(timeout(5000))
      .subscribe({
        next: (response) => {
          this.customerList = response.map(item => {
            return {
             id: item.id,
              name: item.firstName + " " + item.lastName
            };
          });
      }
    });
    }

    refreshEquipmentList() {
      this.http.get<any[]>("http://localhost:8080/data/get-equipment")
      .pipe(timeout(5000))
      .subscribe({
        next: (response) => {
          this.equipmentList = response.map(item => {
            return {
             id: item.id,
              name: item.equipmentType.typeName + " №" + item.serialNumber
            };
          });
      }
    });
    }

    saveTransaction() {
        const transaction: any = {
            equipmentId: this.selectedEquipment,
            customerId: this.selectedCustomer,
            startTime: this.startTime,
            endTime: this.endTime
        };

        this.http.post<any>('http://localhost:8080/data/add-transaction', transaction)
        .pipe(timeout(5000))
        .subscribe({
          next: (response) => {
          this.router.navigate(['/main'])
        }
      })
    }
}
