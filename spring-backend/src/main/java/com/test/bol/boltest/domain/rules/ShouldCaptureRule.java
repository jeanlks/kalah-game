package com.test.bol.boltest.domain.rules;

import java.util.Arrays;

import com.test.bol.boltest.model.Board;
import com.test.bol.boltest.model.BoardPositions;
import com.test.bol.boltest.model.BoardTile;
import com.test.bol.boltest.model.Move;
import com.test.bol.boltest.model.Node;
import com.test.bol.boltest.model.PlayerTurn;


public class ShouldCaptureRule implements Rule {
    Rule next;

    BoardPositions boardPositions;

    
    public ShouldCaptureRule(BoardPositions boardPositions) {
        this.boardPositions = boardPositions;
    }

    @Override
    public void setNext(Rule rule) {
        this.next = rule;
    }

    @Override
    public void process(Move move, Board board, Node lastPosition) {
        if (isPieceOnHisSide(board.getPlayerTurn(), lastPosition)
                && (lastPosition.getNumber() == 0 || lastPosition.getNumber() == 1)) {
            BoardTile oppositePosition = boardPositions.getOppositePosition(lastPosition.getPosition());
            if (board.getBoardMap().get(oppositePosition) != 0) {
                int sum = board.getBoardMap().get(oppositePosition)
                        + board.getBoardMap().get(lastPosition.getPosition());
                board.getBoardMap().replace(oppositePosition, 0);
                board.getBoardMap().replace(lastPosition.getPosition(), 0);
                updatePlayerBigPit(sum, board);
            }
        }
        if(next!= null){
            this.next.process(move, board, lastPosition);
        }
    }

    private void updatePlayerBigPit(int sum, Board board) {
        if (board.getPlayerTurn().equals(PlayerTurn.PLAYER1)) {
            board.getBoardMap().replace(BoardTile.P1_BIG_PIT, board.getBoardMap().get(BoardTile.P1_BIG_PIT) + sum);
        } else {
            board.getBoardMap().replace(BoardTile.P2_BIG_PIT, board.getBoardMap().get(BoardTile.P2_BIG_PIT) + sum);
        }
    }

    private boolean isPieceOnHisSide(PlayerTurn playerTurn, Node lastPosition) {
        if (playerTurn.equals(PlayerTurn.PLAYER1)) {
            if (Arrays.stream(boardPositions.getPlayer1Positions()).anyMatch(lastPosition.getPosition():: equals)) {
                return true;
            }
        } else {
            if (Arrays.stream(boardPositions.getPlayer2Positions()).anyMatch(lastPosition.getPosition()::equals)) {
                return true;
            }
        }
        return false;
    }

}