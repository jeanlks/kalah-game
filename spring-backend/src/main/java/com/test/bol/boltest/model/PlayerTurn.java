package com.test.bol.boltest.model;

/**
 * Player Turn
 * @author Jean Carvalho
 */
public enum PlayerTurn {
    PLAYER1("player1"),
    PLAYER2("player2");

    private String player;

    PlayerTurn(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
