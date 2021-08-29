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
  skillLevelForm: FormGroup | undefined;
  roleForm!: FormGroup;
  showEditForm: boolean = false;
  showRoleForm: boolean = false;
  showSkillLevelForm: boolean = false;
  editSkillLevel: boolean = false;
  submitted: boolean = false;


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

  onShowSkillLevelForm(skillLevel: SkillLevel | undefined = undefined): void {
    this.showSkillLevelForm = true;
    this.skillService.getSkills()
      .subscribe((skills) => {
        this.skills = skills;
        if (skillLevel !== undefined) {
          this.skillLevelForm = this.formBuilder.group({
            id: [skillLevel.id],
            skill: [skillLevel.skill.id, Validators.required],
            level: [skillLevel.level, Validators.required],
          });
        } else {
          this.skillLevelForm = this.formBuilder.group({
            id: [],
            skill: [null, Validators.required], //(this.skills.length) ? this.skills[0].id :
            level: [0, Validators.required],
          });
        }
      });
  }

  addOrEditSkillLevel() {
    if (!this.skillLevelForm) {
      return;
    }
    this.submitted = true;
    if (this.skillLevelForm.invalid) {
      console.log("Form is invalid!");
      return;
    }

    let value = this.skillLevelForm.value;
    for (let skill of this.skills) {
      if (skill.id === Number(value.skill)) {
        value.skill = skill;
        break;
      }
    }
    let skillLevel: SkillLevel = value;
    if (this.user.id !== null) {
      if (skillLevel.id !== null) {
        this.userService.updateSkillLevel(skillLevel, this.user.id, skillLevel.id)
          .subscribe(() => {
            this.userService.getUserById(this.id)
              .subscribe((user) => {
                this.user = user;
              });

          });
      } else {
        this.userService.createSkillLevel(skillLevel, this.user.id)
          .subscribe(() => {
            this.userService.getUserById(this.id)
              .subscribe((user) => {
                this.user = user;
              });
            this.showSkillLevelForm = false;
          });
      }
      this.hideSkillLevelForm();
    }
  }

  deleteSkillLevel(idToDelete: number | null): void {
    if (this.user.id !== null && idToDelete !== null) {
      this.userService.deleteSkillLevel(this.user.id, idToDelete)
        .subscribe(() => {
          this.user.skillLevels = this.user.skillLevels.filter(sl => sl.id !== idToDelete);
          this.userService.getUserById(this.id)
            .subscribe((user) => {
              this.user = user;
            });
        });
    }
  }

  get func() {
    return this.skillLevelForm?.controls;
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
        });
    }
    this.showEditForm = false;
  }

  hideSkillLevelForm() {
    this.skillLevelForm = undefined;
    this.showSkillLevelForm = false;
  }
}
