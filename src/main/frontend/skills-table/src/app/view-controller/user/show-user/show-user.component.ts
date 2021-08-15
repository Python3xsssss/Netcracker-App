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
  showNewSkillForm: boolean = false;


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
      .subscribe(data => {
        this.user = data.result;
      }, error => console.log(error));
  }

  onShowSkillLevelForm(): void {
    this.showSkillLevelForm = true;
    this.addSkillLevelForm = this.formBuilder.group({
      id: [],
      skill: [null, Validators.required],
      level: [0, Validators.required],
      name: ["", Validators.required],
      about: ["", Validators.required]
    });

    this.skillService.getSkills()
      .subscribe(data => {
        this.skills = data.result;
      }, error => console.log(error));
  }

  deleteSkillLevel(idToDelete: number | null): void {
    if (this.user.id !== null && idToDelete !== null) {
      this.userService.deleteSkillLevel(this.user.id, idToDelete)
        .subscribe(data => {
          if (data.httpStatusCode === 200) {
            this.user.skillLevels = this.user.skillLevels.filter(sl => sl.id !== idToDelete);
            this.userService.getUserById(this.id)
              .subscribe(data => {
                this.user = data.result;
              }, error => console.log(error));
          } else {
            console.log("Status: " + data.status + ", Message: " + data.message);
          }

        }, error => console.log(error));
    }
  }

  addSkillLevel() {
    let value = this.addSkillLevelForm.value;

    if (!this.showNewSkillForm) {
      for (let skill of this.skills) {
        if (skill.id === Number(value.skill)) {
          value.skill = skill;
          break;
        }
      }
      let skillLevel: SkillLevel = value;
      this.pushSkillLevel(skillLevel);
    } else {
      let newSkill: Skill = value;
      this.skillService.createSkill(newSkill)
        .subscribe(data => {
          if (data.httpStatusCode === 200) {
            let skillLevel: SkillLevel = value;
            skillLevel.skill = data.result;
            console.log(skillLevel);
            this.pushSkillLevel(skillLevel);
          } else {
            console.log("Status: " + data.status + ", Message: " + data.message);
          }
        })
    }
  }

  pushSkillLevel(skillLevel: SkillLevel) {
    if (this.user.id !== null) {
      this.userService.createSkillLevel(skillLevel, this.user.id)
        .subscribe(data => {
          if (data.httpStatusCode === 200) {
            this.userService.getUserById(this.id)
              .subscribe(data => {
                this.user = data.result;
              }, error => console.log(error));
          } else {
            console.log("Status: " + data.status + ", Message: " + data.message);
          }
          this.showSkillLevelForm = false;
          this.showNewSkillForm = false;
        }, error => console.log(error));
    }
  }

  deleteRole(userId: number | null, role: string) {
    if (userId !== null) {
      this.roleService.deleteRole(userId, role)
        .subscribe(data => {
          if (data.httpStatusCode == 200) {
            this.user.roles.filter(r => r != role);
            this.userService.getUserById(this.id)
              .subscribe(data => {
                this.user = data.result;
              }, error => console.log(error));
          } else {
            console.log("Status: " + data.status + ", Message: " + data.message);
          }
        });
    }
  }

  onShowRoleForm(user: User): void {
    this.roleService.getAllRoles()
      .subscribe(data => {
        this.roles = data.result;
        this.showRoleForm = true;
        this.roleForm = this.formBuilder.group({
          role: ['USER', Validators.required],
        });
      }, error => console.log(error));
  }

  addRole() {
    let newRole: string = this.roleForm.value.role;
    this.roleService.addRole(this.user, newRole)
      .subscribe(data => {
        if (data.httpStatusCode == 200) {
          this.userService.getUserById(this.id)
            .subscribe(data => {
              this.user = data.result;
            }, error => console.log(error));
        } else {
          console.log("Status: " + data.status + ", Message: " + data.message);
        }
        this.showRoleForm = false;
      });
  }

  hideEditForm($event: boolean) {
    this.showEditForm = false;
  }
}
