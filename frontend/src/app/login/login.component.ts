import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  message:string = ""

  constructor (private http: HttpClient, private router: Router) {
    
  }

  login(us: string, ps: string) {
    const data = {
        login : us,
        password: ps
    };


    this.http.post('http://localhost:8080/auth/login', data, { responseType: 'text' })
    .pipe(
        timeout(5000),
    )
    .subscribe({
        next: (response: string) => {
            if (response === 'admin') {
                this.router.navigate(['/main']);
            } else if (response === 'user') {
                this.router.navigate(['/user-main']);
            }
            sessionStorage.setItem('login', us);
        },
        error: (error) => {
            this.message = "Login or password is incorrect";
          
        }
    });

}
}
