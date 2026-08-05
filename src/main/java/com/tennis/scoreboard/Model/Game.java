package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static com.tennis.scoreboard.Enums.PlayerSide.FIRST;
import static com.tennis.scoreboard.Enums.PlayerSide.SECOND;
import static com.tennis.scoreboard.Enums.Score.*;
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class Game {
    private Score firstPlayer = LOVE;
    private Score secondPlayer = LOVE;

    private PlayerSide winner = null;

    private boolean isFinished = false;

    private boolean isTieBreak = false;
    private boolean isTieBreakFinished = false;


    public Game(boolean isTieBreak){
        this.isTieBreak = isTieBreak;
    }

    void addPoints(PlayerSide player){
        if (isFinished ) return;
        if (isTieBreak) {
            if (!isTieBreakFinished) addTieBreakPoints(player);
            return;
        }
        Score own = scoreOf(player);
        Score opp = scoreOf(opponent(player));

        if (own == FORTY || own == AD) {
            handleFortyOrAd(player, own, opp);
        } else {
            setScore(player, addPoints(own));
        }
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


    boolean isFinished(){
        return isTieBreak ? isTieBreakFinished : isFinished;
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
    // метод для tie-break и поля для него
    private Integer firstPlayerTieBreakPoints = 0;
    private Integer secondPlayerTieBreakPoints = 0;

    private void addTieBreakPoints(PlayerSide player){
        firstPlayer = null;
        secondPlayer = null;
        if(player== FIRST){
            ++firstPlayerTieBreakPoints;
            isTieBreakFinished();
        }
        else ++secondPlayerTieBreakPoints;
        isTieBreakFinished();
    }

    private void isTieBreakFinished(){
        if(firstPlayerTieBreakPoints-secondPlayerTieBreakPoints>=2 && firstPlayerTieBreakPoints>=7){
            winner = FIRST;
            isTieBreakFinished = true;
        }
        else if(secondPlayerTieBreakPoints-firstPlayerTieBreakPoints>=2 && secondPlayerTieBreakPoints>=7){
            winner = SECOND;
            isTieBreakFinished = true;
        }
    }

    Integer getTieBreakPoints(PlayerSide player){
        if(player== FIRST){
            return firstPlayerTieBreakPoints == 0 ? null : firstPlayerTieBreakPoints;
        }
        else return secondPlayerTieBreakPoints == 0 ? null : secondPlayerTieBreakPoints;
    }

}
