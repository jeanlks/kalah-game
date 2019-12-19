package com.test.bol.boltest.domain.rules;

import com.test.bol.boltest.model.Board;
import com.test.bol.boltest.model.Move;
import com.test.bol.boltest.model.Node;

public interface Rule {
    void process(Move move, Board board, Node lastPosition);
    void setNext(Rule rule);
}