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
        if(player1 == player2) throw new IllegalArgumentException("Players must be different");
        _player1 = player1;
        _player2 = player2;
    }

    @Transient
    private TennisSet currentSet;
    private int setCount = 0;

    private int firstPlayerSetWin = 0;
    private int secondPlayerSetWin = 0;

    private boolean isFinished = false;

    public void addPoints(PlayerSide player){
        if(isFinished) return;
        if(currentSet==null){
            // здесь проверка на кол-во сетов
            currentSet = new TennisSet();
        }
        currentSet.addPoints(player);
        if(currentSet.isFinished()){
            ++setCount;
            if(PlayerSide.FIRST==currentSet.getWinner()) ++firstPlayerSetWin;
            else ++secondPlayerSetWin;
            handleWithFinishing();
            currentSet = null;
        }
    }
    private void handleWithFinishing(){
        if(firstPlayerSetWin==2 && secondPlayerSetWin<=1) finishMatch(_player1);
        else if(secondPlayerSetWin==2 && firstPlayerSetWin<=1) finishMatch(_player2);

    }

    private void finishMatch(Player player){
        winner = player;
        isFinished = true;
    }

    public Score getScore(PlayerSide player){return currentSet == null ? Score.LOVE : currentSet.getScore(player);}
    public int getGames(PlayerSide player){
        return currentSet == null ? 0 : currentSet.getGames(player);
    }
    public int getSets(PlayerSide player){
        return player==PlayerSide.FIRST ? firstPlayerSetWin:secondPlayerSetWin;
    }


}
