package com.test.bol.boltest.controllers;

import com.test.bol.boltest.exceptions.BoardEmptyException;
import com.test.bol.boltest.exceptions.BoardNotFoundException;
import com.test.bol.boltest.exceptions.IllegalMoveException;
import com.test.bol.boltest.model.Board;
import com.test.bol.boltest.model.BoardDto;
import com.test.bol.boltest.model.Move;
import com.test.bol.boltest.model.MoveDto;
import com.test.bol.boltest.model.Player;
import com.test.bol.boltest.repository.BoardRepository;
import com.test.bol.boltest.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController for the board.
 * @author Jean
 */
@RestController
@RequestMapping(value = "boards")
public class BoardController {

    BoardService service;

    private ModelMapper modelMapper;
    
    @Autowired
    private SimpMessagingTemplate template;

    public BoardController(BoardService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    /**
     * Should list all boards.
     * @return list of boards
     */
    @GetMapping
    public List<BoardDto> getAllBoards(){
        return service
                .getAllBoards().stream()
                .map(b -> convertBoardDto(b))
                .collect(Collectors.toList());
    }

    /**
     * Should create new board for name
     * @param boardName board name
     * @return created board
     */
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardDto dto){
        try {
            Board board= convertBoardToEntity(dto);
            return new ResponseEntity<>(convertBoardDto(service.getExistingBoardOrNew(board)), HttpStatus.OK);
        } catch (BoardEmptyException e){
            return new ResponseEntity<>("An internal error ocurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete all boards
     * @return status for the deletion.
     */
    @DeleteMapping
    public ResponseEntity<?> clearAllBoards() {
        service.clearBoards();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Find board by name.
     * @param boardName board name
     * @return board found or 404 status.
     */
    @GetMapping(value = "/find/{boardName}")
    public ResponseEntity<?> findBoard(@PathVariable("boardName") String boardName){
        try {
            return new ResponseEntity<>(convertBoardDto(service.findBoard(boardName)),HttpStatus.OK);
        } catch (BoardNotFoundException ex) {
            return new ResponseEntity<>("Board not found",HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Should make a move and return board status after move.
     * @param dto move
     * @return Board status
     */
    //@MessageMapping("/move")
    //@SendTo("/topic/moves")
    @PostMapping(value = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makeMove(@RequestBody MoveDto dto){
        try {
            BoardDto board = convertBoardDto(service.makeMove(convertMoveEntity(dto)));
            this.template.convertAndSend("/topic/moves", board);
            return new ResponseEntity<>(board,HttpStatus.OK);
        } catch (BoardNotFoundException e) {
            return new ResponseEntity<>("Board not found",HttpStatus.NOT_FOUND);
        } catch (IllegalMoveException e) {
            return new ResponseEntity<>("Illegal move for player turn",HttpStatus.BAD_REQUEST);
        } catch (BoardEmptyException e) {
            return new ResponseEntity<>("Internal Error for making move.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    BoardDto convertBoardDto(Board board) {
        BoardDto dto = modelMapper.map(board, BoardDto.class);
        dto.setBoardName(board.getName());
        dto.setBoard(board.getBoardMap());
        if(board.getPlayers().size() == 1 ){
            dto.setPlayer1(board.getPlayers().get(0));
        }
        if(board.getPlayers().size() == 2){
            dto.setPlayer2(board.getPlayers().get(1));
        }
        return dto;
    }

    Board convertBoardToEntity(BoardDto dto) { 
        Board board = new Board();
        List<Player> players = new ArrayList<>();
        players.add(dto.getPlayer1());
        players.add(dto.getPlayer2());
        board.setPlayers(players);
        board.setBoardId(dto.getBoardId());
        board.setName(dto.getBoardName());
        return board;
    }

    Move convertMoveEntity(MoveDto moveDto) {
        Move move = modelMapper.map(moveDto, Move.class);
        return move;
    }
}
