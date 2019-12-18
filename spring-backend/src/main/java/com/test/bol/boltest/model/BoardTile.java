package com.test.bol.boltest.model;

/**
 * Board tile pieces.
 * @author Jean Carvalho
 */
public enum BoardTile {
    P1_BIG_PIT("bigPit1"),
    P2_BIG_PIT("bigPit2"),
    P11("p11"),
    P12("p12"),
    P13("p13"),
    P14("p14"),
    P15("p15"),
    P16("p16"),
    P21("p21"),
    P22("p22"),
    P23("p23"),
    P24("p24"),
    P25("p25"),
    P26("p26");

    private String name;

    BoardTile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
