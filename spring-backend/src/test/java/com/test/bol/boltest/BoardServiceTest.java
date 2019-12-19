package com.test.bol.boltest;

import com.test.bol.boltest.exceptions.BoardEmptyException;
import com.test.bol.boltest.exceptions.BoardNotFoundException;
import com.test.bol.boltest.exceptions.IllegalMoveException;
import com.test.bol.boltest.model.Board;
import com.test.bol.boltest.model.Move;
import com.test.bol.boltest.model.PlayerTurn;
import com.test.bol.boltest.repository.BoardRepository;
import com.test.bol.boltest.domain.BoardService;
import com.test.bol.boltest.domain.BoardServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

    // @Mock
    // BoardRepository repository;
    //@InjectMocks
    //BoardService boardService = new BoardServiceImpl(repository);

    @Test
    public void listAllBoards() throws BoardEmptyException{

        // when(repository.findAll()).thenReturn(getAllBoardsMock());

        // List<Board> allBoards = boardService.getAllBoards();
        // Assert.assertEquals(2,allBoards.size());
        // Assert.assertEquals("test",allBoards.get(0).getName());
        // Assert.assertEquals(14,allBoards.get(0).getBoard().getMap().size());

        // Assert.assertEquals("bol",allBoards.get(1).getName());
        // Assert.assertEquals(14,allBoards.get(1).getBoard().getMap().size());
    }


    @Test
    public void shouldGetExistingBoard() throws BoardNotFoundException, BoardEmptyException {
        // when(repository.save(getBoardMock("existingBoard"))).thenReturn(getBoardMock("existingBoard"));
        // when(repository.findFirstByName("existingBoard")).thenReturn(getBoardMock("existingBoard"));
        // boardService.getExistingBoardOrNew("existingBoard");
        // Board board = repository.findFirstByName("existingBoard");
        // Assert.assertEquals(14,board.getBoard().getMap().size());
    }


    @Test(expected = BoardNotFoundException.class)
    public void shouldThrowExceptionNotFoundBoard() throws BoardNotFoundException {
        //boardService.findBoard("notfound");
    }

    @Test(expected = IllegalMoveException.class)
    public void shouldThrowIllegalMoveExceptionPlayer1() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
        // Board boardMock = getBoardMock("board");
        // boardMock.setBoardMap(boardMock.getBoard().getMap());
        // when(repository.findFirstByName("board")).thenReturn(boardMock);
        // when(repository.save(boardMock)).thenReturn(boardMock);
        // boardMock.setPlayerTurn(PlayerTurn.PLAYER1);
        // Move move = new Move("p21","board");
        // boardService.makeMove(move);
    }

    @Test(expected = IllegalMoveException.class)
    public void shouldThrowIllegalMoveExceptionPlayer2() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
        // Board boardMock = getBoardMock("board");
        // boardMock.setBoardMap(boardMock.getBoard().getMap());
        // when(repository.findFirstByName("board")).thenReturn(boardMock);
        // when(repository.save(boardMock)).thenReturn(boardMock);
        // boardMock.setPlayerTurn(PlayerTurn.PLAYER2);
        // Move move = new Move("p11","board");
        // boardService.makeMove(move);
    }

    @Test(expected = IllegalMoveException.class)
    public void shouldThrowIllegalMoveExceptionPositionWithZero() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
        // Board boardMock = getBoardMock("boardZero");
        // boardMock.setBoardMap(boardMock.getBoard().getMap());
        // boardMock.getBoardMap().replace("p11",0);
        // boardMock.getBoardMap().replace("p12",0);
        // boardMock.getBoardMap().replace("p13",0);
        // boardMock.getBoardMap().replace("p14",0);
        // boardMock.getBoardMap().replace("p15",0);
        // boardMock.getBoardMap().replace("p16",1);
        // boardMock.getBoardMap().replace("bigPit1",0);
        // boardMock.getBoardMap().replace("p21",6);
        // boardMock.getBoardMap().replace("p22",6);
        // boardMock.getBoardMap().replace("p23",6);
        // boardMock.getBoardMap().replace("p24",6);
        // boardMock.getBoardMap().replace("p25",6);
        // boardMock.getBoardMap().replace("p26",6);
        // boardMock.getBoardMap().replace("bigPit2",6);
        // when(repository.findFirstByName("boardZero")).thenReturn(boardMock);
        // when(repository.save(boardMock)).thenReturn(boardMock);
        // boardMock.setPlayerTurn(PlayerTurn.PLAYER2);
        // Move move = new Move("p11","boardZero");
        // boardService.makeMove(move);
    }


    @Test(expected = IllegalMoveException.class)
    public void shouldThrowIllegalMoveExceptionUnknownPosition() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
        // Board boardMock = getBoardMock("unknonwPosition");
        // boardMock.setBoardMap(boardMock.getBoard().getMap());
        // when(repository.findFirstByName("unknonwPosition")).thenReturn(boardMock);
        // when(repository.save(boardMock)).thenReturn(boardMock);
        // boardMock.setPlayerTurn(PlayerTurn.PLAYER1);
        // Move move = new Move("p0","unknonwPosition");
        // boardService.makeMove(move);
    }


    @Test
    public void shouldMakeSingleMoveToMancalaAndGetFreeTurn() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
        // Board boardMock = getBoardMock("freeMove");
        // boardMock.setBoardMap(boardMock.getBoard().getMap());
        // when(repository.findFirstByName("freeMove")).thenReturn(boardMock);
        // when(repository.save(boardMock)).thenReturn(boardMock);
        // boardMock.setPlayerTurn(PlayerTurn.PLAYER1);
        // Move move = new Move("p11","freeMove");
        // Board boardMoved = boardService.makeMove(move);
        // Assert.assertEquals(Optional.of(0), Optional.ofNullable(boardMoved.getBoard().getMap().get("p11")));
        // Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoard().getMap().get("p12")));
        // Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoard().getMap().get("p13")));
        // Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoard().getMap().get("p14")));
        // Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoard().getMap().get("p15")));
        // Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoard().getMap().get("p16")));
        // Assert.assertEquals(Optional.of(1), Optional.ofNullable(boardMoved.getBoard().getMap().get("bigPit1")));
        // Assert.assertEquals(PlayerTurn.PLAYER1, boardMoved.getPlayerTurn());
    }

    // @Test
    // public void shouldFinishGame() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
    //     Board boardMock = getBoardMock("finishGame");
    //     boardMock.setBoardMap(boardMock.getBoard().getMap());
    //     boardMock.getBoardMap().replace("p11",0);
    //     boardMock.getBoardMap().replace("p12",0);
    //     boardMock.getBoardMap().replace("p13",0);
    //     boardMock.getBoardMap().replace("p14",0);
    //     boardMock.getBoardMap().replace("p15",0);
    //     boardMock.getBoardMap().replace("p16",1);
    //     boardMock.getBoardMap().replace("bigPit1",0);
    //     boardMock.getBoardMap().replace("p21",6);
    //     boardMock.getBoardMap().replace("p22",6);
    //     boardMock.getBoardMap().replace("p23",6);
    //     boardMock.getBoardMap().replace("p24",6);
    //     boardMock.getBoardMap().replace("p25",6);
    //     boardMock.getBoardMap().replace("p26",6);
    //     boardMock.getBoardMap().replace("bigPit2",6);
    //     when(repository.findFirstByName("finishGame")).thenReturn(boardMock);
    //     when(repository.save(boardMock)).thenReturn(boardMock);
    //     boardMock.setPlayerTurn(PlayerTurn.PLAYER1);
    //     Move move = new Move("p16","finishGame");
    //     Board boardMoved = boardService.makeMove(move);
    //     Assert.assertEquals(Optional.of(1), Optional.ofNullable(boardMoved.getBoard().getMap().get("bigPit1")));
    //     Assert.assertEquals(PlayerTurn.PLAYER1, boardMoved.getPlayerTurn());
    //     Assert.assertEquals(true, boardMoved.getGameFinished());
    // }

    // @Test
    // public void shouldCapturePiecesOppositeSide() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
    //     Board boardMock = getBoardMock("capture");
    //     boardMock.setBoardMap(boardMock.getBoard().getMap());
    //     boardMock.getBoardMap().replace("p11",0);
    //     boardMock.getBoardMap().replace("p12",0);
    //     boardMock.getBoardMap().replace("p13",0);
    //     boardMock.getBoardMap().replace("p14",2);
    //     boardMock.getBoardMap().replace("p15",1);
    //     boardMock.getBoardMap().replace("p16",0);
    //     boardMock.getBoardMap().replace("bigPit1",0);
    //     boardMock.getBoardMap().replace("p21",6);
    //     boardMock.getBoardMap().replace("p22",6);
    //     boardMock.getBoardMap().replace("p23",6);
    //     boardMock.getBoardMap().replace("p24",6);
    //     boardMock.getBoardMap().replace("p25",6);
    //     boardMock.getBoardMap().replace("p26",6);
    //     boardMock.getBoardMap().replace("bigPit2",0);
    //     when(repository.findFirstByName("capture")).thenReturn(boardMock);
    //     when(repository.save(boardMock)).thenReturn(boardMock);
    //     boardMock.setPlayerTurn(PlayerTurn.PLAYER1);
    //     Move move = new Move("p15","capture");
    //     Board boardMoved = boardService.makeMove(move);
    //     Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoardMap().get("bigPit1")));
    //     Assert.assertEquals(PlayerTurn.PLAYER2, boardMoved.getPlayerTurn());
    // }

    // @Test
    // public void shouldNotCapturePiecesOppositeSide() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
    //     Board boardMock = getBoardMock("NotCapture");
    //     boardMock.setBoardMap(boardMock.getBoard().getMap());
    //     boardMock.getBoardMap().replace("p11",0);
    //     boardMock.getBoardMap().replace("p12",0);
    //     boardMock.getBoardMap().replace("p13",0);
    //     boardMock.getBoardMap().replace("p14",2);
    //     boardMock.getBoardMap().replace("p15",5);
    //     boardMock.getBoardMap().replace("p16",0);
    //     boardMock.getBoardMap().replace("bigPit1",0);
    //     boardMock.getBoardMap().replace("p21",6);
    //     boardMock.getBoardMap().replace("p22",6);
    //     boardMock.getBoardMap().replace("p23",0);
    //     boardMock.getBoardMap().replace("p24",6);
    //     boardMock.getBoardMap().replace("p25",6);
    //     boardMock.getBoardMap().replace("p26",6);
    //     boardMock.getBoardMap().replace("bigPit2",0);
    //     when(repository.findFirstByName("NotCapture")).thenReturn(boardMock);
    //     when(repository.save(boardMock)).thenReturn(boardMock);
    //     boardMock.setPlayerTurn(PlayerTurn.PLAYER1);
    //     Move move = new Move("p15","NotCapture");
    //     Board boardMoved = boardService.makeMove(move);
    //     Assert.assertEquals(Optional.of(1), Optional.ofNullable(boardMoved.getBoardMap().get("bigPit1")));
    //     Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoardMap().get("p21")));
    //     Assert.assertEquals(Optional.of(7), Optional.ofNullable(boardMoved.getBoardMap().get("p22")));
    //     Assert.assertEquals(PlayerTurn.PLAYER2, boardMoved.getPlayerTurn());
    // }

    // @Test
    // public void shouldMoveAndChangePlayerTurnToPlayer2() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
    //     Board boardMock = getBoardMock("player1");
    //     boardMock.setBoardMap(boardMock.getBoard().getMap());
    //     when(repository.findFirstByName("player1")).thenReturn(boardMock);
    //     when(repository.save(boardMock)).thenReturn(boardMock);
    //     boardMock.setPlayerTurn(PlayerTurn.PLAYER1);
    //     Move move = new Move("p12","player1");
    //     Board boardMoved = boardService.makeMove(move);
    //     Assert.assertEquals(Optional.of(6),Optional.ofNullable(boardMoved.getBoard().getMap().get("p11")));
    //     Assert.assertEquals(Optional.of(0),Optional.ofNullable(boardMoved.getBoard().getMap().get("p12")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p13")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p14")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p15")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p16")));
    //     Assert.assertEquals(Optional.of(1),Optional.ofNullable(boardMoved.getBoard().getMap().get("bigPit1")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p21")));
    //     Assert.assertEquals(PlayerTurn.PLAYER2, boardMoved.getPlayerTurn());
    // }

    // public void shouldMoveAndChangePlayerTurnToPlayer1() throws IllegalMoveException, BoardNotFoundException, BoardEmptyException {
    //     Board boardMock = getBoardMock("player2");
    //     boardMock.setBoardMap(boardMock.getBoard().getMap());
    //     when(repository.findFirstByName("player2")).thenReturn(boardMock);
    //     when(repository.save(boardMock)).thenReturn(boardMock);
    //     boardMock.setPlayerTurn(PlayerTurn.PLAYER2);
    //     Move move = new Move("p22","player2");
    //     Board boardMoved = boardService.makeMove(move);
    //     Assert.assertEquals(Optional.of(6),Optional.ofNullable(boardMoved.getBoard().getMap().get("p21")));
    //     Assert.assertEquals(Optional.of(0),Optional.ofNullable(boardMoved.getBoard().getMap().get("p22")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p23")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p24")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p25")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p26")));
    //     Assert.assertEquals(Optional.of(1),Optional.ofNullable(boardMoved.getBoard().getMap().get("bigPit2")));
    //     Assert.assertEquals(Optional.of(7),Optional.ofNullable(boardMoved.getBoard().getMap().get("p11")));
    //     Assert.assertEquals(PlayerTurn.PLAYER1, boardMoved.getPlayerTurn());
    // }


    // private Iterable<Board> getAllBoardsMock() {
    //     List<Board> boards = new ArrayList<>();
    //     boards.add(boardService.getNewBoardDefault("test"));
    //     boards.add(boardService.getNewBoardDefault("bol"));
    //     return boards;
    // }


    // private Board getBoardMock(String boardName) {
    //     return boardService.getNewBoardDefault(boardName);
    // }

}
