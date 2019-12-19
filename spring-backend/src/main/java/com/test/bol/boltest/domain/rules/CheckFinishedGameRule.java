package com.test.bol.boltest.domain.rules;

import com.test.bol.boltest.domain.board.Board;
import com.test.bol.boltest.domain.board.BoardPositions;
import com.test.bol.boltest.domain.board.BoardTile;
import com.test.bol.boltest.domain.move.Move;
import com.test.bol.boltest.domain.board.Node;

import java.util.Map;


public class CheckFinishedGameRule implements Rule {

    Rule next;

    @Override
	public void setNext(Rule rule) {
		this.next = rule;
    }
    
    BoardPositions positions; 
    public void Rule(BoardPositions positions){
        this.positions = positions;
    }

    @Override
    public void process(Move move, Board board, Node lastPosition) {
          if (checkPositionsEmpty(positions.getPlayer1Positions(), board.getBoardMap())
                || checkPositionsEmpty(positions.getPlayer2Positions(), board.getBoardMap())) {
            board.setGameFinished(true);
        }
        if(next!= null){
            this.next.process(move, board, lastPosition);
        }
    }

    private boolean checkPositionsEmpty(BoardTile[] playerPositions, Map<BoardTile, Integer> boardMap) {
        for (BoardTile playerPosition : playerPositions) {
            if (boardMap.get(playerPosition) != 0) {
                return false;
            }
        }
        return true;
    }
    
}