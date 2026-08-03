package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import lombok.AccessLevel;
import lombok.Getter;

import jakarta.persistence.Transient;

@Getter(AccessLevel.PACKAGE)
public class TennisSet {
    private Game currentGame;
    private int gameCount = 0;

    private int firstPlayerWin = 0;
    private int secondPlayerWin = 0;

    PlayerSide winner = null;

    // поля для подсчета очков тайбрейка+тесты внизу доделать


    void addPoints(PlayerSide player){
        // проверка на счет и т.д.
        // Если tie-break сделать перевод на другой метод
        if(currentGame==null){
            currentGame = new Game();
        }
        currentGame.addPoints(player);
        if(currentGame.isFinished()){
            ++gameCount;
            // обязательно сделать проверку на кол-во игр
            PlayerSide winner = currentGame.getWinner();
            if(winner==PlayerSide.FIRST) ++firstPlayerWin;
            else ++secondPlayerWin;
            currentGame = null;
        }
    }
    boolean isFinished(){
        // упрощенный вариант, доработать
        if(gameCount==7){
            if(firstPlayerWin>secondPlayerWin) winner = PlayerSide.FIRST;
            else winner = PlayerSide.SECOND;
            return true;
        }
        else{
            return false;
        }
    }

    Score getScore(PlayerSide player){
        return currentGame.getScore(player);
    }
    int getGames(PlayerSide player){
        if(player==PlayerSide.FIRST) return firstPlayerWin;
        else return secondPlayerWin;
    }
}
