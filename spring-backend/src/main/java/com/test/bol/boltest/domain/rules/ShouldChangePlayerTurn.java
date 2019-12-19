package com.test.bol.boltest.domain.rules;

import com.test.bol.boltest.domain.board.Board;
import com.test.bol.boltest.domain.board.BoardTile;
import com.test.bol.boltest.domain.move.Move;
import com.test.bol.boltest.domain.board.Node;
import com.test.bol.boltest.domain.move.PlayerTurn;

public class ShouldChangePlayerTurn implements Rule {
    Rule next;

    @Override
    public void process(Move move, Board board, Node lastPosition) {
        if (!(lastPosition.getPosition() == BoardTile.P1_BIG_PIT)
                && !(lastPosition.getPosition() == BoardTile.P2_BIG_PIT)) {
            changePlayerTurn(board);
        }
        if(next!= null){
            this.next.process(move, board, lastPosition);
        }
    }

    @Override
    public void setNext(Rule rule) {
        this.next = rule;
    }

    private void changePlayerTurn(Board board) {
        if (board.getPlayerTurn().equals(PlayerTurn.PLAYER1)) {
            board.setPlayerTurn(PlayerTurn.PLAYER2);
        } else {
            board.setPlayerTurn(PlayerTurn.PLAYER1);
        }
    }
}