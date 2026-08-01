package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import lombok.AccessLevel;
import lombok.Getter;

import static com.tennis.scoreboard.Enums.Score.*;

@Getter(AccessLevel.PACKAGE)
public class Game {
    private Score firstPlayer = LOVE;
    private Score secondPlayer = LOVE;
    private PlayerSide winner = null;

    void addPoints(PlayerSide player){
        // проверка на то, что матч уже завершен
        if(player==PlayerSide.FIRST){
            firstPlayer = addPoints(firstPlayer);
        }
        else{
            secondPlayer = addPoints(secondPlayer);
        }
    }

    boolean isFinished(){
        // расписать всю логику
        if(firstPlayer== FORTY && secondPlayer.compareTo(THIRTY)<0){
            winner = PlayerSide.FIRST;
            return true;
        }
        else if(secondPlayer== FORTY && firstPlayer.compareTo(THIRTY)<0){
            winner = PlayerSide.SECOND;
            return true;
        }
        else return false;
    }

    // логика для ad должна быть в отдельном методе
    private Score addPoints(Score score){
        return switch(score){
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> AD;
            case AD -> null;
        };
    }

    Score getScore(PlayerSide player){
        if(player==PlayerSide.FIRST) return firstPlayer;
        else return secondPlayer;
    }
    // метод для tie-break и поля для него
}
