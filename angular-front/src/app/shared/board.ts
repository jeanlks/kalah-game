export class Board {
    boardName: string;
    board: {[name: string]: number};
    playerTurn: string;
    gameFinished: boolean;
    winnerText: string;
    constructor() {
    }
}
