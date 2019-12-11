package com.test.bol.boltest.repository;

import com.test.bol.boltest.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for Board Repositories.
 * @author Jean Carvalho
 */
@Repository
public interface BoardRepository extends CrudRepository<Board, Long>{

    /**
     * Find if board with name exists
     * @param boardName board name to search
     * @return board
     */
    Board findFirstByName(String boardName);

}
