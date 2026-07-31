package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.DTO.ScoreResponse;
import com.tennis.scoreboard.Model.Match;
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
        match.addPoints(player);

    }
}
