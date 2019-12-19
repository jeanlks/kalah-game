package com.test.bol.boltest.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    public int number;
    public BoardTile position;
    public Node next;

    public Node(int number, BoardTile position){
        this.number = number;
        this.position = position;
    }
}
