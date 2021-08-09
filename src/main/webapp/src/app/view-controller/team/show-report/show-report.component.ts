import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TeamService} from "../../../service/team.service";
import {MySet} from "../../../utils/myset";
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
  members: Set<User> = new Set<User>(); // todo: MySet?
  skills: Set<Skill> = new Set<Skill>();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private teamService: TeamService,
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.teamService.getTeamById(this.id)
      .subscribe(data => {
          this.team = data.result;
          this.members = this.team.members;
          for (let member of this.members) {
            for (let skillLevel of member.skillLevels) {
              let contains = false;
              for (let skill of this.skills) {
                if (skillLevel.skill.id === skill.id) {
                  contains = true;
                  break;
                }
              }
              if (!contains) {
                this.skills.add(skillLevel.skill);
              }

            }
          }
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
