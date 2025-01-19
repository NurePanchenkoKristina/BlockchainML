import { Component} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-equipment-types',
  templateUrl: './equipment-types.component.html',
  styleUrls: ['./equipment-types.component.css']
})
export class EquipmentTypesComponent {
  list: any = [];

  constructor (private http: HttpClient, private router: Router) {
    this.refreshList();
  }

  add() {
    this.router.navigate(['/add-equipment-type']);
  }

  refreshList() {
    this.http.get<any[]>("http://localhost:8080/data/get-equipment-types")
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
        this.list = response.map(item => {
          return {
           id: item.id,
            typeName: item.typeName,
            costPerMinute: item.costPerMinute, 
            maintenanceCost: item.maintenanceCost
          };
        });
    }
  });
  }

  back() {
    this.router.navigate(['/main']);
  }


}
