package com.tennis.scoreboard.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Set currentSet;
    private int setCount = 0;

    private int firstPlayerSetWin = 0;
    private int secondPlayerSetWin = 0;

    public void addPoints(String player){
        if(currentSet==null){
            // здесь проверка на кол-во сетов
            currentSet = new Set();
            currentSet.addPoints(player);
            ++setCount;
        }
    }

    public boolean isFinished(){
        return false;
    }


}
