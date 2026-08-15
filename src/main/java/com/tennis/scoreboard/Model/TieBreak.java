package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;

public class TieBreak implements Playable {

    private int firstPlayerPoints = 0;
    private int secondPlayerPoints = 0;

    @Override
    public void addPoints(PlayerSide player){
        if (isFinished()) return;
        if (player == PlayerSide.FIRST) ++firstPlayerPoints;
        else ++secondPlayerPoints;
    }

    @Override
    public boolean isFinished(){
        return (firstPlayerPoints >= 7 && firstPlayerPoints - secondPlayerPoints >= 2)
                || (secondPlayerPoints >= 7 && secondPlayerPoints - firstPlayerPoints >= 2);
    }

    @Override
    public PlayerSide getWinner(){
        if (!isFinished()) return null;
        return firstPlayerPoints > secondPlayerPoints ? PlayerSide.FIRST : PlayerSide.SECOND;
    }

    Integer getPoints(PlayerSide player){
        return player == PlayerSide.FIRST ? firstPlayerPoints : secondPlayerPoints;
    }
}
