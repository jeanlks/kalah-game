package com.test.bol.boltest.domain.rules;

import java.util.Arrays;

import com.test.bol.boltest.domain.board.Board;
import com.test.bol.boltest.domain.board.BoardPositions;
import com.test.bol.boltest.domain.board.BoardTile;
import com.test.bol.boltest.domain.board.Node;
import com.test.bol.boltest.domain.move.IllegalMoveException;
import com.test.bol.boltest.domain.move.Move;
import com.test.bol.boltest.domain.player.PlayerTurn;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

@Component
public class IllegalMoveRule implements Rule {
    public static final String ILLEGAL_MOVE_MESSAGE = "Illegal move for player.";
    Rule next;
    BoardPositions boardPositions;

    public IllegalMoveRule(BoardPositions boardPositions) {
        this.boardPositions = boardPositions;
    }

    
    @Override
    public void process(Move move, Board board, Node lastPosition) throws IllegalMoveException {
        if (Arrays.stream(ArrayUtils.addAll(boardPositions.getPlayer1Positions(), boardPositions.getPlayer2Positions()))
                .noneMatch(BoardTile.valueOf(move.getPosition())::equals)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if (board.getBoard().getMap().get(BoardTile.valueOf(move.getPosition())) == 0) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if (board.getPlayerTurn().equals(PlayerTurn.PLAYER1) && Arrays
                .stream(boardPositions.getPlayer2Positions()).anyMatch(BoardTile.valueOf(move.getPosition())::equals)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if (board.getPlayerTurn().equals(PlayerTurn.PLAYER2) && Arrays
                .stream(boardPositions.getPlayer1Positions()).anyMatch(BoardTile.valueOf(move.getPosition())::equals)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        }

        if(this.next != null){
            this.next.process(move, board, lastPosition);
        }

    }

    @Override
    public void setNext(Rule rule) {
        this.next = rule;
    }


}