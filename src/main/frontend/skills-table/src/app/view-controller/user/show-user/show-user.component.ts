import {Component, OnInit} from '@angular/core';

import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user.model";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SkillService} from "../../../service/skill.service";
import {Skill} from "../../../model/skill.model";
import {SkillLevel} from "../../../model/skillLevel.model";
import {RoleService} from "../../../service/role.service";


@Component({
  selector: 'app-show-user',
  templateUrl: './show-user.component.html',
  styleUrls: ['./show-user.component.scss']
})
export class ShowUserComponent implements OnInit {
  id!: number;
  user!: User;
  skills: Skill[] = [];
  roles: string[] = [];
  addSkillLevelForm!: FormGroup;
  roleForm!: FormGroup;
  showEditForm: boolean = false;
  showRoleForm: boolean = false;
  showSkillLevelForm: boolean = false;
  editSkillLevel: boolean = false;


  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private skillService: SkillService,
    private roleService: RoleService
  ) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.userService.getUserById(this.id)
      .subscribe((user) => {
        this.user = user;
      });
  }

  onShowSkillLevelForm(): void {
    this.showSkillLevelForm = true;
    this.addSkillLevelForm = this.formBuilder.group({
      id: [],
      skill: [null, Validators.required],
      level: [0, Validators.required],
    });

    this.skillService.getSkills()
      .subscribe((skills) => {
        this.skills = skills;
      });
  }

  deleteSkillLevel(idToDelete: number | null): void {
    if (this.user.id !== null && idToDelete !== null) {
      this.userService.deleteSkillLevel(this.user.id, idToDelete)
        .subscribe(data => {
          this.user.skillLevels = this.user.skillLevels.filter(sl => sl.id !== idToDelete);
          this.userService.getUserById(this.id)
            .subscribe((user) => {
              this.user = user;
            });
        });
    }
  }

  addSkillLevel() {
    let value = this.addSkillLevelForm.value;

    for (let skill of this.skills) {
      if (skill.id === Number(value.skill)) {
        value.skill = skill;
        break;
      }
    }
    let skillLevel: SkillLevel = value;
    if (this.user.id !== null) {
      this.userService.createSkillLevel(skillLevel, this.user.id)
        .subscribe(() => {
          this.userService.getUserById(this.id)
            .subscribe((user) => {
              this.user = user;
            });
          this.showSkillLevelForm = false;
        });
    }
  }

  deleteRole(userId: number | null, role: string) {
    if (userId !== null) {
      this.roleService.deleteRole(userId, role)
        .subscribe(() => {
          this.user.roles.filter(r => r != role);
          this.userService.getUserById(this.id)
            .subscribe((user) => {
              this.user = user;
            });
        });
    }
  }

  onShowRoleForm(user: User): void {
    this.roleService.getAllRoles()
      .subscribe((roles) => {
        this.roles = roles.map(role => role.toString());
        this.showRoleForm = true;
        this.roleForm = this.formBuilder.group({
          role: ['USER', Validators.required],
        });
      });
  }

  addRole() {
    let newRole: string = this.roleForm.value.role;
    this.roleService.addRole(this.user, newRole)
      .subscribe(() => {
        this.userService.getUserById(this.id)
          .subscribe((user) => {
            this.user = user;
          });
        this.showRoleForm = false;
      });
  }

  hideEditForm($event: boolean) {
    if ($event) {
      this.userService.getUserById(this.id)
        .subscribe((user) => {
          this.user = user;
          this.showEditForm = false;
        });
    } else {
      this.showEditForm = false;
    }

  }
}
