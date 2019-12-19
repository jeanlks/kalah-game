package com.test.bol.boltest.domain.board;

import com.test.bol.boltest.domain.move.PlayerTurn;
import com.test.bol.boltest.domain.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Board data transfer object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private String boardName;
    private String boardId;
    private Map<BoardTile, Integer> board;
    private PlayerTurn playerTurn;
    private Player player1;
    private Player player2;
    private Boolean gameFinished;
}
