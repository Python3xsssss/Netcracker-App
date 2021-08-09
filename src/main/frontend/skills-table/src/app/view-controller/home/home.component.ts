import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TokenStorageService} from "../../service/token-storage.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router
  ) {

  }

  ngOnInit(): void {
  }

  checkIfLoggedIn(): boolean {
    return !!this.tokenStorageService.getToken();
  }

  signIn() {
    this.router.navigate(['auth/sign-in']);
  }
}
