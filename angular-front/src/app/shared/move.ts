export class Move {
    position: string;
    boardName: string;
    boardId: string;

    constructor(position: string, boardName: string, boardId: string) {
        this.position = position;
        this.boardName = boardName;
        this.boardId = boardId;
    }
}
