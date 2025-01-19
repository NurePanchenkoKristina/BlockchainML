import { Component} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent {
    
  list:any = [];

  constructor (private http: HttpClient, private router: Router) {
    this.refreshList();
  }


  refreshList() {
    this.http.get<any[]>("http://localhost:8080/data/get-transactions")
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
        this.list = response.map(item => {
          return {
           id: item.id,
            typeName: item.equipment.equipmentType.typeName,
            lastName: item.customer.lastName, 
            firstName: item.customer.firstName,
            startTime: item.startTime,
            endTime: item.endTime,
            cost: item.cost
          };
        });
    }
  });
  }

  add() {
    this.router.navigate(["/add-transaction"]);
  }

  analysis() {
    this.router.navigate(["/analysis"]);
  }

  equipment() {
    this.router.navigate(["/equipment"]);
  }

  equipmentTypes() {
    this.router.navigate(["/equipment-types"]);
  }

  logout() {
    sessionStorage.removeItem('login');
    this.router.navigate(["/login"]);
  }
}
