package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
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

    @ManyToOne
    @JoinColumn(name="winner_id")
    private Player winner;

    public Match(Player player1, Player player2){
        _player1 = player1;
        _player2 = player2;
    }

    @Transient
    private TennisSet currentSet;
    private int setCount = 0;

    private int firstPlayerSetWin = 0;
    private int secondPlayerSetWin = 0;

    public void addPoints(PlayerSide player){
        if(currentSet==null){
            // здесь проверка на кол-во сетов
            currentSet = new TennisSet();
        }
        currentSet.addPoints(player);
        if(currentSet.isFinished()){
            ++setCount;

            currentSet=null;
        }
    }

    public boolean isFinished(){
        // упрощенный вариант, доработать
        if(setCount==3){
            if(firstPlayerSetWin>secondPlayerSetWin) winner = _player1;
            else winner = _player2;
            return true;
        }
        else return false;
    }

    public Score getScore(PlayerSide player){
        return currentSet.getScore(player);
    }
    public int getGames(PlayerSide player){
        return currentSet.getGames(player);
    }
    public int getSets(PlayerSide player){
        if(player==PlayerSide.FIRST) return firstPlayerSetWin;
        else return secondPlayerSetWin;
    }


}
