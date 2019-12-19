package com.test.bol.boltest.domain.board;

import com.test.bol.boltest.domain.board.BoardTile;
import com.test.bol.boltest.domain.board.CircularLinkedList;
import com.test.bol.boltest.domain.player.PlayerTurn;
import com.test.bol.boltest.domain.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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
    private String boardId;
    private String name;
    @Transient
    private CircularLinkedList board;
    @ElementCollection
    @CollectionTable(name = "board_item_mapping",
            joinColumns = {@JoinColumn(name = "board_id", referencedColumnName = "boardId")})
    @MapKeyColumn(name = "board_position")
    @Column(name = "qnt")
    private Map<BoardTile, Integer> boardMap;
    private PlayerTurn playerTurn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players;

    private Boolean gameFinished;
    
}
