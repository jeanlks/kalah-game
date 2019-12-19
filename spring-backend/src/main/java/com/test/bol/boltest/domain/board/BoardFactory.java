package com.test.bol.boltest.domain.board;

import com.test.bol.boltest.domain.player.PlayerTurn;

import java.util.*;

public class BoardFactory { 

    public Board getBoard() {
        Board board = new Board();
        int index_random_player = new Random().nextInt(2);
        board.setBoard(this.getCircularBoard());
        board.setPlayerTurn(Arrays.asList(PlayerTurn.PLAYER1, PlayerTurn.PLAYER2).get(index_random_player));
        return board;
    }


    private CircularLinkedList getCircularBoard() {
        CircularLinkedList board = new CircularLinkedList();
        board.addNodeAtEnd(6, BoardTile.P11);
        board.addNodeAtEnd(6, BoardTile.P12);
        board.addNodeAtEnd(6, BoardTile.P13);
        board.addNodeAtEnd(6, BoardTile.P14);
        board.addNodeAtEnd(6, BoardTile.P15);
        board.addNodeAtEnd(6, BoardTile.P16);
        board.addNodeAtEnd(0, BoardTile.P1_BIG_PIT);
        board.addNodeAtEnd(6, BoardTile.P21);
        board.addNodeAtEnd(6, BoardTile.P22);
        board.addNodeAtEnd(6, BoardTile.P23);
        board.addNodeAtEnd(6, BoardTile.P24);
        board.addNodeAtEnd(6, BoardTile.P25);
        board.addNodeAtEnd(6, BoardTile.P26);
        board.addNodeAtEnd(0, BoardTile.P2_BIG_PIT);
        return board;
    }
}