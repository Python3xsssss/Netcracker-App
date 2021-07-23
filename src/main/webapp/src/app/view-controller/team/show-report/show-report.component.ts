import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TeamService} from "../../../service/team.service";
import {UserService} from "../../../service/user.service";
import {SkillService} from "../../../service/skill.service";
import {User} from "../../../model/user.model";
import {Skill} from "../../../model/skill.model";
import {Team} from "../../../model/team.model";

@Component({
  selector: 'app-show-report',
  templateUrl: './show-report.component.html',
  styleUrls: ['./show-report.component.css']
})
export class ShowReportComponent implements OnInit {
  id!: number;
  team!: Team;
  members: Set<User> = new Set<User>();
  skills: Set<Skill> = new Set<Skill>();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private teamService: TeamService,
    private userService: UserService,
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.teamService.getTeamById(this.id)
      .subscribe(data => {
          this.team = data.result;
          this.members = this.team.members;
          for (let member of this.members) {
            this.userService.getUserById(member.id)
              .subscribe(data => {
                  member = data.result;
                  console.log(member);
                  for (let skillLevel of member.skillLevels) {
                    this.skills.add(skillLevel.skill);
                  }
                }
              );
          }
          console.log(this.skills);
        }
      )
  }

  getSkillLevel(member: User, skill: Skill): string {
    for (let skillLevel of member.skillLevels) {
      if (skillLevel.skill.id === skill.id)
        return String(skillLevel.level);
    }
    return "-";
  }

  backToTeam(): void {
    this.router.navigate(['team', this.id]);
  }
}
