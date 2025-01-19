import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor (private http: HttpClient, private router: Router) {
    
  }
  
    register(us:any, ps:any, fn:any, ln:any, pt:any, bd:any) {
      const data = {
          firstName: fn,
          lastName: ln,
          patronymic: pt,
          birthDate: bd,
          login: us, 
          hashedPassword: ps
      };
      this.http.post<any>('http://localhost:8080/auth/register', data)
      .pipe(timeout(5000))
      .subscribe({
        next: (response) => {
            this.router.navigate(['/login'])
      }
    })
    }
}
