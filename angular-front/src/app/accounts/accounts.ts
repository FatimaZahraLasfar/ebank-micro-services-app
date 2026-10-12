import {Component, inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-accounts',
  imports: [],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {
  private http = inject(HttpClient);
  accounts$ = this.http.get("http://localhost:9999/EBANK-SERVICE/accounts")
}
