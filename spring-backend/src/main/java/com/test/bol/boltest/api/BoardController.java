package com.test.bol.boltest.api;

import com.test.bol.boltest.domain.board.BoardEmptyException;
import com.test.bol.boltest.domain.board.BoardNotFoundException;
import com.test.bol.boltest.domain.move.IllegalMoveException;
import com.test.bol.boltest.domain.board.Board;
import com.test.bol.boltest.domain.board.BoardDto;
import com.test.bol.boltest.domain.move.Move;
import com.test.bol.boltest.domain.move.MoveDto;
import com.test.bol.boltest.domain.player.Player;
import com.test.bol.boltest.controller.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
     * @param dto board
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
     * Should create new board for name
     * @param boardName board name
     * @return created board
     */
    @PostMapping(value = "/join")
    public ResponseEntity<?> joinBoard(@RequestBody BoardDto dto){
        Board board= convertBoardToEntity(dto);
        return new ResponseEntity<>(convertBoardDto(service.joinBoard(board)), HttpStatus.OK); 
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
        Player player1 =  Optional.of(board.getPlayers())
                                                  .filter(players -> players.size() == 1)
                                                  .map(players -> players.get(0))
                                                  .orElse(null);
        Player player2 =  Optional.of(board.getPlayers())
                                                  .filter(players -> players.size() == 2)
                                                  .map(players -> players.get(1))
                                                  .orElse(null);
        dto.setPlayer1(player1);
        dto.setPlayer2(player2);
        return dto;
    }

    Board convertBoardToEntity(BoardDto dto) { 
        Board board = new Board();
        List<Player> players = new ArrayList<>();
        Optional<Player> player1 = Optional.ofNullable(dto.getPlayer1());
        player1.ifPresent(player -> players.add(player));
        Optional<Player> player2 = Optional.ofNullable(dto.getPlayer2());
        player2.ifPresent(player -> players.add(player));
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
