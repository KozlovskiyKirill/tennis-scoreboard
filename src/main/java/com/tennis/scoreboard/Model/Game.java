package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import lombok.AccessLevel;
import lombok.Getter;

import static com.tennis.scoreboard.Enums.PlayerSide.FIRST;
import static com.tennis.scoreboard.Enums.PlayerSide.SECOND;
import static com.tennis.scoreboard.Enums.Score.*;

@Getter(AccessLevel.PACKAGE)
public class Game implements Playable {
    private Score firstPlayer = LOVE;
    private Score secondPlayer = LOVE;

    private PlayerSide winner = null;
    private boolean isFinished = false;

    @Override
    public void addPoints(PlayerSide player){
        if (isFinished) return;
        Score own = scoreOf(player);
        Score opp = scoreOf(opponent(player));

        if (own == FORTY || own == AD) {
            handleFortyOrAd(player, own, opp);
        } else {
            setScore(player, addPoints(own));
        }
    }

    @Override
    public boolean isFinished(){
        return isFinished;
    }

    @Override
    public PlayerSide getWinner(){
        return winner;
    }

    private Score scoreOf(PlayerSide player) {
        return player == FIRST ? firstPlayer : secondPlayer;
    }

    private void setScore(PlayerSide player, Score score) {
        if (player == FIRST) firstPlayer = score;
        else secondPlayer = score;
    }

    private void handleFortyOrAd(PlayerSide player, Score own, Score opp){
        if (own == AD) {
            finish(player);
        } else if (opp == FORTY) {
            setScore(player, AD);
        } else if (opp == AD) {
            setScore(opponent(player), FORTY);
        } else {
            finish(player);
        }
    }

    private PlayerSide opponent(PlayerSide side) {
       return side == FIRST ? SECOND : FIRST;
    }

    private void finish(PlayerSide player){
        isFinished = true;
        winner = player;
    }

    private Score addPoints(Score score){
        return switch(score){
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            default -> score;
        };
    }

    Score getScore(PlayerSide player){
        if(player== FIRST) return firstPlayer;
        else return secondPlayer;
    }
}
