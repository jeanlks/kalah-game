package com.test.bol.boltest.controller;

import com.test.bol.boltest.domain.board.BoardEmptyException;
import com.test.bol.boltest.domain.board.BoardNotFoundException;
import com.test.bol.boltest.domain.board.JoinBoardException;
import com.test.bol.boltest.domain.move.IllegalMoveException;
import com.test.bol.boltest.domain.board.Board;
import com.test.bol.boltest.domain.move.Move;

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
     * Join player to existent board
     * @param board board
     * @return board
     */
    Board joinBoard(Board board) throws JoinBoardException;

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
}
