package com.test.bol.boltest.service;

import com.test.bol.boltest.exceptions.BoardEmptyException;
import com.test.bol.boltest.exceptions.BoardNotFoundException;
import com.test.bol.boltest.exceptions.IllegalMoveException;
import com.test.bol.boltest.model.*;
import com.test.bol.boltest.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

import static com.test.bol.boltest.model.PlayerTurn.PLAYER1;
import static com.test.bol.boltest.model.PlayerTurn.PLAYER2;

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

    public BoardServiceImpl(BoardRepository repository) {
        this.repository = repository;
        this.boardFactory = new BoardFactory();
    }

    public BoardTile player1Positions[] = new BoardTile[] { 
        BoardTile.P11, BoardTile.P12, BoardTile.P13,
        BoardTile.P14, BoardTile.P15, BoardTile.P16
    };
    public BoardTile player2Positions[] = new BoardTile[] { 
        BoardTile.P21, BoardTile.P22, BoardTile.P23,
        BoardTile.P24, BoardTile.P25, BoardTile.P26 
    };

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
        Board board = findBoard(move.getBoardName());
        board.setBoard(getCircularLinkedListFromMap(board.getBoardMap()));
        checkIllegalMove(move, board);
        CircularLinkedList boardTable = board.getBoard();
        Node lastPosition = moveBoard(boardTable, move.getPosition(), board.getPlayerTurn());
        board.setBoard(boardTable);
        board.setBoardMap(board.getBoard().getMap());
        shouldCapture(board, lastPosition);
        checkLastPositionAndChangePlayer(lastPosition, board);
        checkFinishedGame(board);
        repository.save(board);
        return board;
    }

    private void shouldCapture(Board board, Node lastPosition) {
        if (isPieceOnHisSide(board.getPlayerTurn(), lastPosition)
                && (lastPosition.getNumber() == 0 || lastPosition.getNumber() == 1)) {
            BoardTile oppositePosition = getOppositePosition(lastPosition.getPosition());
            if (board.getBoardMap().get(oppositePosition) != 0) {
                int sum = board.getBoardMap().get(oppositePosition)
                        + board.getBoardMap().get(lastPosition.getPosition());
                board.getBoardMap().replace(oppositePosition, 0);
                board.getBoardMap().replace(lastPosition.getPosition(), 0);
                updatePlayerBigPit(sum, board);
            }
        }
    }

    private void updatePlayerBigPit(int sum, Board board) {
        if (board.getPlayerTurn().equals(PlayerTurn.PLAYER1)) {
            board.getBoardMap()
                 .replace(BoardTile.P1_BIG_PIT, board.getBoardMap().get(BoardTile.P1_BIG_PIT) + sum);
        } else {
            board.getBoardMap()
                 .replace(BoardTile.P2_BIG_PIT, board.getBoardMap().get(BoardTile.P2_BIG_PIT) + sum);
        }
    }

    private BoardTile getOppositePosition(BoardTile position) {
        Map<BoardTile, BoardTile> opposites = getAllOppositePositions();
        return opposites.get(position);
    }

    private Map<BoardTile, BoardTile> getAllOppositePositions() {
        Map<BoardTile, BoardTile> opps = new HashMap<>();
        opps.put(BoardTile.P11, BoardTile.P26);
        opps.put(BoardTile.P12, BoardTile.P25);
        opps.put(BoardTile.P13, BoardTile.P24);
        opps.put(BoardTile.P14, BoardTile.P23);
        opps.put(BoardTile.P15, BoardTile.P22);
        opps.put(BoardTile.P16, BoardTile.P21);

        opps.put(BoardTile.P26, BoardTile.P11);
        opps.put(BoardTile.P25, BoardTile.P12);
        opps.put(BoardTile.P24, BoardTile.P13);
        opps.put(BoardTile.P23, BoardTile.P14);
        opps.put(BoardTile.P22, BoardTile.P15);
        opps.put(BoardTile.P21, BoardTile.P16);
        return opps;
    }

    private boolean isPieceOnHisSide(PlayerTurn playerTurn, Node lastPosition) {
        if (playerTurn.equals(PlayerTurn.PLAYER1)) {
            if (Arrays.stream(player1Positions).anyMatch(lastPosition.getPosition():: equals)) {
                return true;
            }
        } else {
            if (Arrays.stream(player2Positions).anyMatch(lastPosition.getPosition()::equals)) {
                return true;
            }
        }
        return false;
    }

    private Board checkFinishedGame(Board board) {
        if (checkPositionsEmpty(player1Positions, board.getBoardMap())
                || checkPositionsEmpty(player2Positions, board.getBoardMap())) {
            board.setGameFinished(true);
        }
        return board;
    }

    private boolean checkPositionsEmpty(BoardTile[] playerPositions, Map<BoardTile, Integer> boardMap) {
        for (BoardTile playerPosition : playerPositions) {
            if (boardMap.get(playerPosition) != 0) {
                return false;
            }
        }
        return true;
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

    private void checkIllegalMove(Move move, Board board) throws IllegalMoveException, BoardEmptyException {
        if (Arrays.stream(ArrayUtils.addAll(player1Positions, player2Positions))
                .noneMatch(move.getPosition()::equals)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if (board.getBoard().getMap().get(move.getPosition()) == 0) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if (board.getPlayerTurn().equals(PlayerTurn.PLAYER1)
                && Arrays.stream(player2Positions).anyMatch(move.getPosition()::equals)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if (board.getPlayerTurn().equals(PlayerTurn.PLAYER2)
                && Arrays.stream(player1Positions).anyMatch(move.getPosition()::equals)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        }
    }

    @Override
    public void clearBoards() {
        repository.deleteAll();
    }

    private void checkLastPositionAndChangePlayer(Node lastPosition, Board board) {
        if (!(lastPosition.getPosition() == BoardTile.P1_BIG_PIT)
                && !(lastPosition.getPosition() == BoardTile.P2_BIG_PIT)) {
            changePlayerTurn(board);
        }
    }

    private void changePlayerTurn(Board board) {
        if (board.getPlayerTurn().equals(PlayerTurn.PLAYER1)) {
            board.setPlayerTurn(PlayerTurn.PLAYER2);
        } else {
            board.setPlayerTurn(PlayerTurn.PLAYER1);
        }
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
        board.setPlayerTurn(Arrays.asList(PLAYER1, PLAYER2).get(index_random_player));
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

    private Node moveBoard(CircularLinkedList boardTable, String position, PlayerTurn playerTurn) {
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


