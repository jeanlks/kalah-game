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
 * @author Jean Carvalho
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    public static final String ILLEGAL_MOVE_MESSAGE = "Illegal move for player.";
    public static final String P1_BIGPIT = "bigPit1";
    public static final String P2_BIGPIT = "bigPit2";
    BoardRepository repository;

    public BoardServiceImpl(BoardRepository repository) {
        this.repository = repository;
    }

    public String player1Positions[] = new String[]{"p11", "p12","p13","p14","p15", "p16"};
    public String player2Positions[] = new String[]{"p21", "p22","p23","p24","p25", "p26"};

    @GetMapping
    public List<Board> getAllBoards(){
        return (List) repository.findAll();
    }

    @Override
    public Board findBoard(String boardId) throws BoardNotFoundException {
        Board boardFound = repository.findFirstByBoardId(boardId);
        log.info("Searching for board with id: ", boardId);
        if(boardFound == null) {
            log.info("Board not found");
            throw new BoardNotFoundException("Board not found for id: "+ boardId);
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
        if(isPieceOnHisSide(board.getPlayerTurn(), lastPosition)
                && (lastPosition.getNumber() == 0 || lastPosition.getNumber() == 1)) {
            String oppositePosition = getOppositePosition(lastPosition.getPosition());
            if(board.getBoardMap().get(oppositePosition) != 0) {
                int sum  = board.getBoardMap().get(oppositePosition) + board.getBoardMap().get(lastPosition.getPosition());
                board.getBoardMap().replace(oppositePosition, 0);
                board.getBoardMap().replace(lastPosition.getPosition(), 0);
                updatePlayerBigPit(sum, board);
            }
        }
    }

    private void updatePlayerBigPit(int sum, Board board) {
        if(board.getPlayerTurn().equals(PlayerTurn.PLAYER1)){
            board.getBoardMap().replace(P1_BIGPIT, board.getBoardMap().get(P1_BIGPIT) + sum);
        } else {
            board.getBoardMap().replace(P2_BIGPIT, board.getBoardMap().get(P2_BIGPIT) + sum);
        }
    }

    private String getOppositePosition(String position) {
       Map<String, String> opposites =  getAllOppositePositions();
       return opposites.get(position);
    }

    private Map<String,String> getAllOppositePositions() {
        Map<String, String> opps = new HashMap<>();
        opps.put("p11","p26");
        opps.put("p12","p25");
        opps.put("p13","p24");
        opps.put("p14","p23");
        opps.put("p15","p22");
        opps.put("p16","p21");

        opps.put("p26","p11");
        opps.put("p25","p12");
        opps.put("p24","p13");
        opps.put("p23","p14");
        opps.put("p22","p15");
        opps.put("p21","p16");
        return opps;
    }

    private boolean isPieceOnHisSide(PlayerTurn playerTurn, Node lastPosition) {
        if(playerTurn.equals(PlayerTurn.PLAYER1)){
            if(Arrays.stream(player1Positions).anyMatch(lastPosition.getPosition()::equalsIgnoreCase)){
                return true;
            }
        } else {
            if(Arrays.stream(player2Positions).anyMatch(lastPosition.getPosition()::equalsIgnoreCase)){
                return true;
            }
        }
        return false;
    }

    private Board checkFinishedGame(Board board) {
        if(checkPositionsEmpty(player1Positions, board.getBoardMap())
                || checkPositionsEmpty(player2Positions, board.getBoardMap())){
            board.setGameFinished(true);
        }
       return board;
    }

    private boolean checkPositionsEmpty(String[] playerPositions, Map<String, Integer> boardMap) {
        for (String playerPosition : playerPositions) {
            if(boardMap.get(playerPosition) != 0 ) {
                return false;
            }
        }
        return true;
    }

    private CircularLinkedList getCircularLinkedListFromMap(Map<String, Integer> map) {
        CircularLinkedList board = new CircularLinkedList();
        board.addNodeAtEnd(map.get("p11"), "p11");
        board.addNodeAtEnd(map.get("p12"), "p12");
        board.addNodeAtEnd(map.get("p13"), "p13");
        board.addNodeAtEnd(map.get("p14"), "p14");
        board.addNodeAtEnd(map.get("p15"), "p15");
        board.addNodeAtEnd(map.get("p16"), "p16");
        board.addNodeAtEnd(map.get(P1_BIGPIT), P1_BIGPIT);
        board.addNodeAtEnd(map.get("p21"), "p21");
        board.addNodeAtEnd(map.get("p22"), "p22");
        board.addNodeAtEnd(map.get("p23"), "p23");
        board.addNodeAtEnd(map.get("p24"), "p24");
        board.addNodeAtEnd(map.get("p25"), "p25");
        board.addNodeAtEnd(map.get("p26"), "p26");
        board.addNodeAtEnd(map.get(P2_BIGPIT), P2_BIGPIT);
        return board;
    }

    private void checkIllegalMove(Move move, Board board) throws IllegalMoveException, BoardEmptyException {
        if(Arrays.stream(ArrayUtils.addAll(player1Positions, player2Positions)).noneMatch(move.getPosition()::equalsIgnoreCase)){
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if(board.getBoard().getMap().get(move.getPosition()) == 0) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        }  else if(board.getPlayerTurn().equals(PlayerTurn.PLAYER1) && Arrays.stream(player2Positions).anyMatch(move.getPosition()::equalsIgnoreCase)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        } else if (board.getPlayerTurn().equals(PlayerTurn.PLAYER2) && Arrays.stream(player1Positions).anyMatch(move.getPosition()::equalsIgnoreCase)) {
            throw new IllegalMoveException(ILLEGAL_MOVE_MESSAGE);
        }
    }

    @Override
    public void clearBoards() {
        repository.deleteAll();
    }



    private void checkLastPositionAndChangePlayer(Node lastPosition, Board board) {
        if(!lastPosition.getPosition().equalsIgnoreCase(P1_BIGPIT) &&
                !lastPosition.getPosition().equalsIgnoreCase(P2_BIGPIT)){
            changePlayerTurn(board);
        }
    }

    private void changePlayerTurn(Board board) {
        if(board.getPlayerTurn().equals(PlayerTurn.PLAYER1)){
            board.setPlayerTurn(PlayerTurn.PLAYER2);
        } else {
            board.setPlayerTurn(PlayerTurn.PLAYER1);
        }
    }


    @Override
    public Board getExistingBoardOrNew(Board board) throws BoardEmptyException{
        Board boardFromDatabase = repository.findFirstByBoardId(board.getBoardId());
        if(boardFromDatabase!=null){
            board.setBoardMap(boardFromDatabase.getBoardMap());
            return board;
        } else {
            setNewBoardDefault(board);
            board.setBoardMap(board.getBoard().getMap());
            return repository.save(board);
        }
    }

    @Override
    public void setNewBoardDefault(Board board) {
        board.setBoard(getDefaultBoard());
        int index_random_player = new Random().nextInt(2);
        board.setPlayerTurn(Arrays.asList(PLAYER1, PLAYER2).get(index_random_player));
    }

    private CircularLinkedList getDefaultBoard() {
        CircularLinkedList board = new CircularLinkedList();
        board.addNodeAtEnd(6, "p11");
        board.addNodeAtEnd(6, "p12");
        board.addNodeAtEnd(6, "p13");
        board.addNodeAtEnd(6, "p14");
        board.addNodeAtEnd(6, "p15");
        board.addNodeAtEnd(6, "p16");
        board.addNodeAtEnd(0, P1_BIGPIT);
        board.addNodeAtEnd(6, "p21");
        board.addNodeAtEnd(6, "p22");
        board.addNodeAtEnd(6, "p23");
        board.addNodeAtEnd(6, "p24");
        board.addNodeAtEnd(6, "p25");
        board.addNodeAtEnd(6, "p26");
        board.addNodeAtEnd(0, P2_BIGPIT);
        return board;
    }

    private Node moveBoard(CircularLinkedList boardTable, String position, PlayerTurn playerTurn) {
        Node temp = boardTable.head;
        do {
            temp = temp.next;
        } while(!temp.getPosition().equalsIgnoreCase(position));

        int number_moves = temp.getNumber();
        temp.setNumber(0);
        do {
            temp = temp.next;
            if(shouldSkipMancala(temp.getPosition(),playerTurn)){
                temp = temp.next;
            }
            temp.setNumber(temp.getNumber()+1);
            number_moves = number_moves-1;
        } while(number_moves!=0);

        return temp;
    }

    private boolean shouldSkipMancala(String position, PlayerTurn playerTurn) {
        if(position.equalsIgnoreCase(P1_BIGPIT) && playerTurn.equals(PlayerTurn.PLAYER2)){
            return true;
        } else if(position.equalsIgnoreCase(P2_BIGPIT) && playerTurn.equals(PlayerTurn.PLAYER1)) {
            return true;
        }

        return false;
    }
}
