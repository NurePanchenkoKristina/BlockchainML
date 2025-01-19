import { Component} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-equipment',
  templateUrl: './equipment.component.html',
  styleUrls: ['./equipment.component.css']
})
export class EquipmentComponent {
  list:any = [];

  constructor (private http: HttpClient, private router: Router) {
    this.refreshList();
  }


  refreshList() {
    this.http.get<any[]>("http://localhost:8080/data/get-equipment")
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
        this.list = response.map(item => {
          return {
           id: item.id,
            typeName: item.equipmentType.typeName,
            serialNumber: item.serialNumber, 
  
          };
        });
    }
  });
  }

  add() {
    this.router.navigate(['/add-equipment']);
  }
  
  back() {
    this.router.navigate(['/main']);
  }
}
