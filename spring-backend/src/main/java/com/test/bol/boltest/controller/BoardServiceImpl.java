package com.test.bol.boltest.controller;

import com.test.bol.boltest.controller.BoardService;
import com.test.bol.boltest.domain.board.*;
import com.test.bol.boltest.domain.board.BoardEmptyException;
import com.test.bol.boltest.domain.move.IllegalMoveException;
import com.test.bol.boltest.domain.move.*;
import com.test.bol.boltest.domain.player.PlayerTurn;
import com.test.bol.boltest.domain.rules.CheckFinishedGameRule;
import com.test.bol.boltest.domain.rules.IllegalMoveRule;
import com.test.bol.boltest.domain.rules.ShouldCaptureRule;
import com.test.bol.boltest.domain.rules.ShouldChangePlayerTurnRule;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * Implementation for board service.
 * 
 * @author Jean Carvalho
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    public static final String ILLEGAL_MOVE_MESSAGE = "Illegal move for player.";

    BoardRepository repository;
    BoardFactory boardFactory;
    BoardPositions boardPositions;
    CheckFinishedGameRule checkFinishedGameRule;
    ShouldCaptureRule shouldCaptureRule;
    ShouldChangePlayerTurnRule shouldChangePlayerTurnRule; 
    IllegalMoveRule illegalMoveRule;

    public BoardServiceImpl(BoardRepository repository, BoardPositions boardPositions,
                            CheckFinishedGameRule checkFinishedGameRule, ShouldCaptureRule shouldCaptureRule,
                            ShouldChangePlayerTurnRule shouldChangePlayerTurnRule, IllegalMoveRule illegalMoveRule) {
        this.repository = repository;
        this.boardFactory = new BoardFactory();
        this.boardPositions = boardPositions;
        this.checkFinishedGameRule = checkFinishedGameRule;
        this.shouldCaptureRule = shouldCaptureRule;
        this.shouldChangePlayerTurnRule = shouldChangePlayerTurnRule;
        this.illegalMoveRule = illegalMoveRule;
    }



    @GetMapping
    public List<Board> getAllBoards() {
        return (List) repository.findAll();
    }

    @Override
    public Board findBoard(String boardId) throws BoardNotFoundException {
        Board boardFound = repository.findFirstByBoardId(boardId);
        log.info("Searching for board with id: ", boardId);
        if (boardFound == null) {
            log.info("Board not found");
            throw new BoardNotFoundException("Board not found for id: " + boardId);
        }
        return boardFound;
    }

    @Override
    public Board makeMove(Move move) throws BoardNotFoundException, IllegalMoveException, BoardEmptyException {
        shouldCaptureRule.setNext(this.shouldChangePlayerTurnRule);
        this.shouldChangePlayerTurnRule.setNext(this.checkFinishedGameRule);
        Board board = findBoard(move.getBoardName());
        board.setBoard(getCircularLinkedListFromMap(board.getBoardMap()));
        this.illegalMoveRule.process(move, board, null);
        CircularLinkedList boardTable = board.getBoard();
        Node lastPosition = moveBoard(boardTable, BoardTile.valueOf(move.getPosition()), board.getPlayerTurn());
        board.setBoard(boardTable);
        board.setBoardMap(board.getBoard().getMap());
        shouldCaptureRule.process(move, board, lastPosition);
        repository.save(board);
        return board;
    }

    private CircularLinkedList getCircularLinkedListFromMap(Map<BoardTile, Integer> map) {
        CircularLinkedList board = new CircularLinkedList();
        board.addNodeAtEnd(map.get(BoardTile.P11), BoardTile.P11);
        board.addNodeAtEnd(map.get(BoardTile.P12), BoardTile.P12);
        board.addNodeAtEnd(map.get(BoardTile.P13), BoardTile.P13);
        board.addNodeAtEnd(map.get(BoardTile.P14), BoardTile.P14);
        board.addNodeAtEnd(map.get(BoardTile.P15), BoardTile.P15);
        board.addNodeAtEnd(map.get(BoardTile.P16), BoardTile.P16);
        board.addNodeAtEnd(map.get(BoardTile.P1_BIG_PIT), BoardTile.P1_BIG_PIT);
        board.addNodeAtEnd(map.get(BoardTile.P21), BoardTile.P21);
        board.addNodeAtEnd(map.get(BoardTile.P22), BoardTile.P22);
        board.addNodeAtEnd(map.get(BoardTile.P23), BoardTile.P23);
        board.addNodeAtEnd(map.get(BoardTile.P24), BoardTile.P24);
        board.addNodeAtEnd(map.get(BoardTile.P25), BoardTile.P25);
        board.addNodeAtEnd(map.get(BoardTile.P26), BoardTile.P26);
        board.addNodeAtEnd(map.get(BoardTile.P2_BIG_PIT), BoardTile.P2_BIG_PIT);
        return board;
    }

    @Override
    public void clearBoards() {
        repository.deleteAll();
    }

    @Override
    public Board getExistingBoardOrNew(Board board) throws BoardEmptyException {
        Board boardFromDatabase = repository.findFirstByBoardId(board.getBoardId());
        if (boardFromDatabase != null) {
            board.setBoardMap(boardFromDatabase.getBoardMap());
            return board;
        } else {
            Board defaultBoard = boardFactory.getBoard();
            defaultBoard.setBoardId(board.getBoardId());
            defaultBoard.setName(board.getName());
            defaultBoard.setPlayers(board.getPlayers());
            defaultBoard.setBoardMap(defaultBoard.getBoard().getMap());
            return repository.save(defaultBoard);
        }
    }

    @Override
    public Board joinBoard(Board board) {
        Board boardFromRepository = repository.findFirstByBoardId(board.getBoardId());
        boardFromRepository.getPlayers().add(board.getPlayers().get(0));
        return repository.save(boardFromRepository);
    }

    @Override
    public void setNewBoardDefault(Board board) {
        board.setBoard(getDefaultBoard());
        int index_random_player = new Random().nextInt(2);
        board.setPlayerTurn(Arrays.asList(PlayerTurn.PLAYER1, PlayerTurn.PLAYER2).get(index_random_player));
    }

    private CircularLinkedList getDefaultBoard() {
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

    private Node moveBoard(CircularLinkedList boardTable, BoardTile position, PlayerTurn playerTurn) {
        Node temp = boardTable.head;
        do {
            temp = temp.next;
        } while (!temp.getPosition().equals(position));

        int number_moves = temp.getNumber();
        temp.setNumber(0);
        do {
            temp = temp.next;
            if (shouldSkipMancala(temp.getPosition(), playerTurn)) {
                temp = temp.next;
            }
            temp.setNumber(temp.getNumber() + 1);
            number_moves = number_moves - 1;
        } while (number_moves != 0);

        return temp;
    }

    private boolean shouldSkipMancala(BoardTile position, PlayerTurn playerTurn) {
        if (position.equals(BoardTile.P1_BIG_PIT) && playerTurn.equals(PlayerTurn.PLAYER2)) {
            return true;
        } else if (position.equals(BoardTile.P2_BIG_PIT) && playerTurn.equals(PlayerTurn.PLAYER1)) {
            return true;
        }

        return false;
    }
}


