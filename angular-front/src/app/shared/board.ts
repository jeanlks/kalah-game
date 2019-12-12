export class Board {
    boardName: string;
    boardId: string;
    board: {[name: string]: number};
    playerTurn: string;
    gameFinished: boolean;
    winnerText: string;
    constructor() {
    }
}
