import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { MatTableModule } from '@angular/material';
import { GameService } from '../shared/game.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {

  displayedColumns: string[] = ['Game name', 'Player', 'Join'];
  dataSource = [];

  constructor(private gameService: GameService,  private router: Router) {

  }

  ngOnInit() {
    this.gameService.getAllBoards().subscribe(boards => {
      this.dataSource = boards;
    });
  }

  joinGame(boardId) {
    this.router.navigate(['/game', boardId]);
  }

}
