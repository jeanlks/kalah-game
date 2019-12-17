import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as uuid from 'uuid';
import { GameService } from '../shared/game.service';
import { Tile } from '../shared/tile';
import { Move } from '../shared/move';
import { Board } from '../shared/board';
import { AppModalComponent } from './app-modal';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  title = 'Bol.com Mancala Game';
  board: Board = new Board();
  boardMap: Map<string, number> = new Map<string, number>();
  gameService: GameService;

  dialog: MatDialog;
  webSocketEndPoint = 'http://localhost:8080/ws';
  topic = '/topic/moves';
  stompClient = null;

  constructor(gameService: GameService, dialog: MatDialog) {
    this.gameService = gameService;
    this.dialog = dialog;
  }



  tiles: Tile[] = [
    { text: '0', cols: 1, rows: 2, color: 'lightgreen', name: 'bigPit2' },
    { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p26' },
    { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p25' },
    { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p24' },
    { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p23' },
    { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p22' },
    { text: '6', cols: 1, rows: 1, color: 'lightgreen', name: 'p21' },
    { text: '0', cols: 1, rows: 2, color: 'lightblue', name: 'bigPit1' },
    { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p11' },
    { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p12' },
    { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p13' },
    { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p14' },
    { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p15' },
    { text: '6', cols: 1, rows: 1, color: 'lightblue', name: 'p16' },
  ];

  ngOnInit() {
    this.gameService.createBoard('test').subscribe(boardUpdated => {
      this.refreshBoard(boardUpdated);
    });
    this.connect();
    console.log(this.getIdLocalStorage('game_id'));
  }

  move(tile: Tile) {
    console.log(tile);
    const move: Move = new Move(tile.name, this.board.boardName, this.getIdLocalStorage('user_id'));
    this.gameService.makeMove(move).subscribe((boardUpdated) => {
      this.refreshBoard(boardUpdated);
    });
  }

  updateBoardMap(board) {
    Object.keys(board).forEach(key => {
      this.addBoardItem(key, board[key]);
    });
  }


  addBoardItem(key: string, value: number) {
    this.boardMap.set(key, value);
  }

  createNewGameAddDeleteOld() {
    this.gameService.clearAllBoards().subscribe((c) => {
      this.gameService.createBoard('test').subscribe(boardUpdated => {
        this.refreshBoard(boardUpdated);
      });
    });
  }

  refreshBoard(board: Board) {
    this.updateBoardMap(board.board);
    if (board.gameFinished === true) {
      board.winnerText = this.getWinnerText();
      const dialogRef = this.dialog.open(AppModalComponent, {
        width: '250px',
        data: board
      });

      dialogRef.afterClosed().subscribe(result => {
        this.createNewGameAddDeleteOld();
        this.board = new Board();
      });
    } else {
      this.board = board;
      this.tiles.forEach((tile) => {
        tile.text = String(this.boardMap.get(tile.name));
      });
    }
  }

  getWinnerText(): string {
    if (this.boardMap.get('bigPit1') > this.boardMap.get('bigPit2')) {
      return 'Congrats!! Game finished and Player 1 won!';
    } else if (this.boardMap.get('bigPit2') > this.boardMap.get('bigPit1')) {
      return 'Congrats!! Game finished and Player 2 won!';
    } else {
      return 'Congrats!! Game finished with a draw';
    }
  }

  connect() {
    console.log('Initialize WebSocket Connection');
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, function (frame) {
      _this.stompClient.subscribe(_this.topic, function (event) {
        _this.handleMessage(event.body);
      });
      //_this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected');
  }

  handleMessage(message) {
    this.refreshBoard(JSON.parse(message));
  }

  errorCallBack(error) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this.connect();
    }, 5000);
  }

  getIdLocalStorage(field, forceCreateNew: boolean = false, ) {
    let id = localStorage.getItem(field);
    if (!forceCreateNew && id) {
      return id;
    }
    id = uuid.v4();
    localStorage.setItem(field, id);
    return id;
  }

  

}
