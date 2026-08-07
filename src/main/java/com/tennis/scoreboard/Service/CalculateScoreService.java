package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.DTO.PlayerScore;
import com.tennis.scoreboard.DTO.ScoreResponse;
import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Model.Player;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CalculateScoreService {
    private final OnGoingMatches onGoingMatches;
    private final SaveMatch saveMatch;

    public CalculateScoreService(OnGoingMatches match, SaveMatch saveMatch){
        onGoingMatches = match;
        this.saveMatch = saveMatch;
    }

    public ScoreResponse addPoints(UUID uuid, String player){
        Match match = onGoingMatches.find(uuid);
        // валидация матча
        // валидация имени
        Player player1 = match.get_player1();
        if(player1.getName().equals(player)){
            match.addPoints(PlayerSide.FIRST);}
        else{
            match.addPoints(PlayerSide.SECOND);
        }

        saveMatch.saveMatch(match);
        return gatherStatistics(match);
    }

    public ScoreResponse receiveMatchStatistics (Match match){
        return gatherStatistics(match);
    }


    private ScoreResponse gatherStatistics(Match match){
        PlayerScore firstPlayer = new PlayerScore(match.get_player1().getName(),
                mapScore(match.getScore(PlayerSide.FIRST)),match.getGames(PlayerSide.FIRST),
                match.getSets(PlayerSide.FIRST),match.getTieBreakPoints(PlayerSide.FIRST));
        PlayerScore secondPlayer = new PlayerScore(match.get_player2().getName(),
                mapScore(match.getScore(PlayerSide.SECOND)),match.getGames(PlayerSide.SECOND),
                match.getSets(PlayerSide.SECOND),match.getTieBreakPoints(PlayerSide.SECOND));
        Player winner = match.getWinner();
        String winnerName = winner != null ? winner.getName() : null;
        return new ScoreResponse(firstPlayer, secondPlayer, winnerName);
    }

    private String mapScore(Score score){
        if (score==null) return null;
        return switch (score){
            case LOVE -> "0";
            case FIFTEEN -> "15";
            case THIRTY -> "30";
            case FORTY -> "40";
            case AD -> "AD";
            default -> null;
        };
    }
}
