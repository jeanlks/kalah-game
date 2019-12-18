import { Player } from './player';

export class Board {
    boardName: string;
    boardId: string;
    board: {[name: string]: number};
    playerTurn: string;
    gameFinished: boolean;
    winnerText: string;
    player1: Player;
    player2: Player;
    constructor() {
    }
}
