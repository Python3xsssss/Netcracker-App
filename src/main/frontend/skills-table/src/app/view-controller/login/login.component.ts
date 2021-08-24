import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';
import {TokenStorageService} from '../../service/token-storage.service';
import {AuthRequest} from "../../model/auth.request";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  isLoggedIn = false;
  isLoginFailed = false;
  loading = false;
  submitted = false;
  returnUrl!: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private notificationService: NotificationService,
    private tokenStorage: TokenStorageService
  ) {
    if (this.tokenStorage.getToken()) {
      this.router.navigate(['home']);
    }
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      this.isLoginFailed = true;
      return;
    }

    this.loading = true;
    let request: AuthRequest = this.loginForm.value;
    this.authService.login(request)
      .subscribe((jwtResponse) => {
          this.tokenStorage.saveToken(jwtResponse.jwtToken);
          this.tokenStorage.saveUser(jwtResponse.user);

          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.router.navigate([this.returnUrl]).then(() => {
            window.location.reload();
          });
        },
        error => {
          this.isLoginFailed = true;
          this.loading = false;
          this.notificationService.showError(error.error.message);
        }
      );
  }
}


