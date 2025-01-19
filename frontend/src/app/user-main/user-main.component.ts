import { Component} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-main',
  templateUrl: './user-main.component.html',
  styleUrls: ['./user-main.component.css']
})
export class UserMainComponent {
  list:any = []; 

  constructor (private http: HttpClient, private router: Router) {
    this.refreshList();
  }

  refreshList() {
    const login = sessionStorage.getItem('login');
    this.http.get<any[]>("http://localhost:8080/data/get-transactions/" + login)
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
        this.list = response.map(item => {
          return {
            eqipment: item.equipment.equipmentType.typeName + " №" + item.equipment.serialNumber,
            startTime: item.startTime,
            endTime: item.endTime,
            cost: item.cost
          };
        });
    }
  });
  }

  add() {
    this.router.navigate(["/add-transaction-user"]);
  }

  logout() {
    sessionStorage.removeItem('login');
    this.router.navigate(["/login"]);
  }
}
