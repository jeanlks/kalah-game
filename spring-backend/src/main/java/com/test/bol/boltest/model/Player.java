package com.test.bol.boltest.model;

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