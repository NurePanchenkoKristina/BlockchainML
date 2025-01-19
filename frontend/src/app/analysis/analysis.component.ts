import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-analysis',
  templateUrl: './analysis.component.html',
  styleUrls: ['./analysis.component.css']
})
export class AnalysisComponent {
  list:any = [];

  constructor (private http: HttpClient, private router: Router) {
    this.predictDemand();
  }

  predictDemand() {
    this.http.get<any>("http://localhost:8080/ml/predict-demand-random-forest")
      .pipe(timeout(5000))
      .subscribe({
        next: (response) => {
          this.list = Object.keys(response).map(itemName => {
            return {
              name: itemName,
              value: response[itemName]
            };
          });
        },
        error: (error) => {
          console.error('Error fetching item list:', error);
        }
      });
  }

  optimizeInventory() {
    this.http.get<any>("http://localhost:8080/ml/optimize-inventory")
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
        this.list = Object.keys(response).map(itemName => {
          return {
            name: itemName,
            value: response[itemName]
          };
        });
      },
      error: (error) => {
        console.error('Error fetching item list:', error);
      }
    });
  }

  optimizePrice() {
    this.http.get<any>("http://localhost:8080/ml/optimize-price")
    .pipe(timeout(5000))
    .subscribe({
      next: (response) => {
        this.list = Object.keys(response).map(itemName => {
          return {
            name: itemName,
            value: response[itemName]
          };
        });
      },
      error: (error) => {
        console.error('Error fetching item list:', error);
      }
    });
  }
}
