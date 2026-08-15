package com.tennis.scoreboard.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Matches")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne(optional = false)
    @JoinColumn(name="Player1")
    private PlayerEntity _player1;

    @ManyToOne(optional = false)
    @JoinColumn(name="Player2")
    private PlayerEntity _player2;

    @ManyToOne(optional = false)
    @JoinColumn(name="Winner")
    private PlayerEntity _winner;

    public MatchEntity(PlayerEntity firstPlayer, PlayerEntity secondPlayer, PlayerEntity winner){
        if(firstPlayer == null || secondPlayer == null || winner == null)
            throw new IllegalArgumentException("Players must not be null");
        if (firstPlayer.getName().equals(secondPlayer.getName()))
            throw new IllegalArgumentException("Players must be different");
        _player1 = firstPlayer;
        _player2 = secondPlayer;
        _winner = winner;
    }
}
