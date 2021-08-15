import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TeamService} from "../../../service/team.service";
import {MySet} from "../../../utils/myset";
import {User} from "../../../model/user.model";
import {Skill} from "../../../model/skill.model";
import {Team} from "../../../model/team.model";
import {Plotly} from "angular-plotly.js/lib/plotly.interface";

class CountAvg {
  count: number;
  average: number;

  constructor(count: number, average: number) {
    this.count = count;
    this.average = average;
  }
}

@Component({
  selector: 'app-show-report',
  templateUrl: './show-report.component.html',
  styleUrls: ['./show-report.component.scss']
})
export class ShowReportComponent implements OnInit {
  id!: number;
  team!: Team;
  members: User[] = [];
  skills: Skill[] = [];
  hist: any[] = [];
  showPlot = false;

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
                this.skills.push(skillLevel.skill);
              }

            }
          }
          this.dataHist();
        }
      );

  }

  getSkillLevel(member: User, skill: Skill): string {
    for (let skillLevel of member.skillLevels) {
      if (skillLevel.skill.id === skill.id)
        return String(skillLevel.level);
    }
    return "-";
  }

  getCountAndAverage(skill: Skill): CountAvg {
    let average: number = 0;
    let count: number = 0;
    for (let member of this.members) {
      let level = Number(this.getSkillLevel(member, skill));
      if (!isNaN(level)) {
        average += level;
        count++;
      }
    }
    return {count: count, average: (count == 0) ? 0 : average / count};
  }

  dataHist() {
    let skillNames = this.skills.map(skill => skill.name);
    let result = this.skills.map(skill => this.getCountAndAverage(skill));
    let traceCounts = {
      x: skillNames,
      y: result.map(obj => obj.count),
      name: 'People with skill',
      type: 'bar'
    };
    let traceAverages = {
      x: skillNames,
      y: result.map(obj => obj.average),
      name: 'Average level of skill',
      type: 'bar'
    };
    console.log(traceCounts);
    console.log(traceAverages);
    this.hist = [traceCounts, traceAverages];
    this.showPlot = true;
  }

  backToTeam(): void {
    this.router.navigate(['team', this.id]);
  }
}
