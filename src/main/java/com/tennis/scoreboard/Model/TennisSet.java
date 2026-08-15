package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
public class TennisSet {
    private Playable currentGame;
    private int gameCount = 0;

    private int firstPlayerWin = 0;
    private int secondPlayerWin = 0;

    PlayerSide winner = null;

    private boolean isFinished = false;

    private boolean tieBreak = false;


    void addPoints(PlayerSide player){
        if(isFinished) return;
        if(currentGame==null){
            currentGame = tieBreak ? new TieBreak() : new Game();
        }
        currentGame.addPoints(player);
        if(currentGame.isFinished()){
            ++gameCount;
            PlayerSide winner = currentGame.getWinner();
            if(winner==PlayerSide.FIRST) ++firstPlayerWin;
            else ++secondPlayerWin;
            handleSet();
            currentGame = null;
        }
    }
    boolean isFinished(){
        return isFinished;
    }

    private void handleSet(){

        if(firstPlayerWin==6 && secondPlayerWin<=4){
            finishSet(PlayerSide.FIRST);
        } else if(secondPlayerWin==6 && firstPlayerWin<=4){
            finishSet(PlayerSide.SECOND);
        } else if(firstPlayerWin==6 && secondPlayerWin==6){
            tieBreak = true;
        } else if(firstPlayerWin==7 || secondPlayerWin==7){
            if (firstPlayerWin == 7) {
                finishSet(PlayerSide.FIRST);
            } else {
                finishSet(PlayerSide.SECOND);
            }
        }

    }

    private void finishSet(PlayerSide player){
        winner = player;
        isFinished = true;
    }

    Score getScore(PlayerSide player){
        if(currentGame==null) return Score.LOVE;
        if(currentGame instanceof Game game) return game.getScore(player);
        return null;
    }
    int getGames(PlayerSide player){
        return player==PlayerSide.FIRST ? firstPlayerWin:secondPlayerWin;
    }

    Integer getTieBreakPoints(PlayerSide player){
        if(currentGame instanceof TieBreak tieBreak) return tieBreak.getPoints(player);
        return null;
    }
}
