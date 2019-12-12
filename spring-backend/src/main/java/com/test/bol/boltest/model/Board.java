package com.test.bol.boltest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import com.test.bol.boltest.model.Player;

/**
 * Board entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String boardId;
    private String name;
    @Transient
    private CircularLinkedList board;
    @ElementCollection
    @CollectionTable(name = "board_item_mapping",
            joinColumns = {@JoinColumn(name = "board_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "board_position")
    @Column(name = "qnt")
    private Map<String, Integer> boardMap;
    private PlayerTurn playerTurn;

    @OneToMany(mappedBy = "playerId")
    private List<Player> players;

    private Boolean gameFinished;
}
