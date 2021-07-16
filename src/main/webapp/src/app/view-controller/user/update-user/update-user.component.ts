import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {
  id!: number;
  user!: User;

  constructor(private router: Router, private route: ActivatedRoute, private userService: UserService) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.userService.getUserById(this.id).subscribe(data => {
      this.user = data.result;
    }, error => console.log(error));
  }

  onSubmit() {
    this.userService.updateUser(this.user).subscribe(data => {
      this.router.navigate(['users']);
    }, error => console.log(error));
  }
}
