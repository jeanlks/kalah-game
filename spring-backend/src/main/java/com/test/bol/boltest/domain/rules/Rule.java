package com.test.bol.boltest.domain.rules;

import com.test.bol.boltest.domain.board.Board;
import com.test.bol.boltest.domain.move.IllegalMoveException;
import com.test.bol.boltest.domain.move.Move;
import com.test.bol.boltest.domain.board.Node;

public interface Rule {
    void process(Move move, Board board, Node lastPosition) throws IllegalMoveException; 
    void setNext(Rule rule);
}