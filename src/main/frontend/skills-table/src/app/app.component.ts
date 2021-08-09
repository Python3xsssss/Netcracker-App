import {Component} from '@angular/core';

import {Router} from '@angular/router';
import {TokenStorageService} from "./service/token-storage.service";
import {User} from "./model/user.model";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  show: boolean = false;
  title = 'Skills Table';
  showAdminBoard = false;
  user?: User | null;


  constructor(private router: Router, private tokenStorageService: TokenStorageService) {
  }


  ngOnInit(): void {
    if (this.checkIfLoggedIn()) {
      this.user = this.tokenStorageService.getUser();
      if (this.user !== null) {
        this.showAdminBoard = this.user.roles.includes("ADMIN");
      }
    }
  }

  checkIfLoggedIn(): boolean {
    return !!this.tokenStorageService.getToken();
  }

  signOut(): void {
    this.tokenStorageService.signOut();
    this.router.navigate(['home']).then(() => {
      window.location.reload();
    });

  }

}
