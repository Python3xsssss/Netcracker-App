import {Component, OnInit} from '@angular/core';
import {Role} from "../../model/role.model";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';
import {TokenStorageService} from '../../service/token-storage.service';
import {AuthRequest} from "../../model/auth.request";
import {AlertService} from "../../service/alert.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  loading = false;
  submitted = false;
  returnUrl!: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService,
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

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      this.isLoginFailed = true;
      return;
    }

    this.loading = true;
    let request: AuthRequest = this.loginForm.value;
    this.authService.login(request).subscribe(
      data => {
        this.tokenStorage.saveToken(data.result.jwtToken);
        this.tokenStorage.saveUser(data.result.user);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.router.navigate([this.returnUrl]).then(() => {
          window.location.reload();
        });
      },
      error => {
        this.alertService.error(error);
        this.isLoginFailed = true;
        this.loading = false;
      }
    );
  }
}


