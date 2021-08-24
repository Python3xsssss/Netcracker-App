import { Component, OnInit } from '@angular/core';

import {ActivatedRoute, Router} from "@angular/router";
import {TeamService} from "../../../service/team.service";
import {Team} from "../../../model/team.model";

@Component({
  selector: 'app-show-team',
  templateUrl: './show-team.component.html',
  styleUrls: ['./show-team.component.scss']
})
export class ShowTeamComponent implements OnInit {

  id!: number;
  team!: Team;

  constructor(private router: Router, private route: ActivatedRoute, private teamService: TeamService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.teamService.getTeamById(this.id)
      .subscribe((team) => {
        this.team = team;
      });
  }

  addMember(): void {
    this.router.navigate(['add-user']);
  }

  showReport(): void {
    this.router.navigate([this.router.url + "/show-report"]);
  }

}
