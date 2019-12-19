package com.test.bol.boltest.domain.board;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class BoardPositions { 
    private BoardTile player1Positions[] = new BoardTile[] { 
        BoardTile.P11, BoardTile.P12, BoardTile.P13,
        BoardTile.P14, BoardTile.P15, BoardTile.P16
    };
    private  BoardTile player2Positions[] = new BoardTile[] { 
        BoardTile.P21, BoardTile.P22, BoardTile.P23,
        BoardTile.P24, BoardTile.P25, BoardTile.P26 
    };


    public BoardTile getOppositePosition(BoardTile position) {
        Map<BoardTile, BoardTile> opposites = getAllOppositePositions();
        return opposites.get(position);
    }

    public Map<BoardTile, BoardTile> getAllOppositePositions() {
        Map<BoardTile, BoardTile> opps = new HashMap<>();
        opps.put(BoardTile.P11, BoardTile.P26);
        opps.put(BoardTile.P12, BoardTile.P25);
        opps.put(BoardTile.P13, BoardTile.P24);
        opps.put(BoardTile.P14, BoardTile.P23);
        opps.put(BoardTile.P15, BoardTile.P22);
        opps.put(BoardTile.P16, BoardTile.P21);

        opps.put(BoardTile.P26, BoardTile.P11);
        opps.put(BoardTile.P25, BoardTile.P12);
        opps.put(BoardTile.P24, BoardTile.P13);
        opps.put(BoardTile.P23, BoardTile.P14);
        opps.put(BoardTile.P22, BoardTile.P15);
        opps.put(BoardTile.P21, BoardTile.P16);
        return opps;
    }

}