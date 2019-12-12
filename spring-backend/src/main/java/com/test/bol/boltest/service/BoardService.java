package com.test.bol.boltest.service;

import com.test.bol.boltest.exceptions.BoardEmptyException;
import com.test.bol.boltest.exceptions.BoardNotFoundException;
import com.test.bol.boltest.exceptions.IllegalMoveException;
import com.test.bol.boltest.model.Board;
import com.test.bol.boltest.model.Move;

import java.util.List;

/**
 * Board service
 * @author Jean Carvalho
 */
public interface BoardService {
    /**
     * Get all the boards existent
     * @return list of boards
     */
    List<Board> getAllBoards();

    /**
     * Find if board with name exists
     * @param boardName board name to search
     * @return board
     * @throws BoardNotFoundException board not found
     */
    Board findBoard(String boardName) throws BoardNotFoundException;

    /**
     * Create a new board for the game if it does not exists
     * or get the existing one.
     * @param boardName boardName
     * @return board
     */
    Board getExistingBoardOrNew(Board board) throws BoardEmptyException;

    /**
     * Make a move inside the board game.
     * @param move move object with board name and position of the move
     * @return board status
     * @throws BoardNotFoundException board not found
     */
    Board makeMove(Move move) throws BoardNotFoundException, IllegalMoveException, BoardEmptyException;

    /**
     * Delete all boards
     */
    void clearBoards();

    /**
     * Get a default board for starting the game
     * @param boardName board name
     * @return new default board
     */
    void setNewBoardDefault(Board board);
}
