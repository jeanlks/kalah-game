package com.test.bol.boltest.domain.player;

import javax.persistence.*;
import lombok.*;

/**
 * Player entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    @Id
    private String playerId;
    private String playerName;
}