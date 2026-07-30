package com.tennis.scoreboard.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table (name = "Matches")
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer ID;

    @ManyToOne
    @JoinColumn(name="player1_id")
    private Player _player1;

    @ManyToOne
    @JoinColumn(name="player2_id")
    private Player _player2;

    @Column(name="winner")
    private int winner;

    public Match(Player player1, Player player2){
        _player1 = player1;
        _player2 = player2;
    }
}
