package com.tennis.scoreboard.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table (name = "Matches")
public class Match {
    @Id
    private int ID;

    @ManyToOne
    @JoinColumn(name="Player1")
    private int Player1;

    @ManyToOne
    @JoinColumn(name="Player2")
    private int Player2;

    @Column(name="Winner")
    private int Winner;
}
