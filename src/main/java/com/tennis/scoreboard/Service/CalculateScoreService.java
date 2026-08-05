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

    public CalculateScoreService(OnGoingMatches match){
        onGoingMatches = match;
    }

    public ScoreResponse addPoints(UUID uuid, String player){
        Match match = onGoingMatches.find(uuid);
        // валидация матча
        // валидация имени
        Player player1 = match.get_player1();
        if(player1.getName().equals(player)){
            System.out.println("Зашел за первым игроком");
            match.addPoints(PlayerSide.FIRST);}
        else{
            System.out.println("Зашел за вторым игроком");
            match.addPoints(PlayerSide.SECOND);
        }
        return gatherStatistics(match);
    }

    public ScoreResponse receiveMatchStatistics (Match match){
        return gatherStatistics(match);
    }

    private ScoreResponse gatherStatistics(Match match){
        // пока для простоты tieBreak будет null
        PlayerScore firstPlayer = new PlayerScore(match.get_player1().getName(),
                mapScore(match.getScore(PlayerSide.FIRST)),match.getGames(PlayerSide.FIRST),
                match.getSets(PlayerSide.FIRST),null);
        PlayerScore secondPlayer = new PlayerScore(match.get_player2().getName(),
                mapScore(match.getScore(PlayerSide.SECOND)),match.getGames(PlayerSide.SECOND),
                match.getSets(PlayerSide.SECOND),null);
        Player winner = match.getWinner();
        String winnerName = winner != null ? winner.getName() : null;
        return new ScoreResponse(firstPlayer, secondPlayer, winnerName);
    }

    private int mapScore(Score score){
        return switch (score){
            case LOVE -> 0;
            case FIFTEEN -> 15;
            case THIRTY -> 30;
            case FORTY -> 40;
            case AD -> 50;
        };
    }
}
