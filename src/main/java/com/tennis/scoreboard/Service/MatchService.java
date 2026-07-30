package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.DTO.CreateMatchRequest;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Model.Player;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MatchService {
    private final PlayerService playerService;
    private final OnGoingMatches onGoingMatches;

    public MatchService(PlayerService playerService, OnGoingMatches onGoingMatches){
        this.playerService = playerService;
        this.onGoingMatches = onGoingMatches;
    }

    public UUID createMatch(CreateMatchRequest response){
        String firstPlayer = response.firstPlayer();
        String secondPlayer = response.secondPlayer();

        Player fPlayer = playerService.findOrCreate(firstPlayer);
        Player sPlayer = playerService.findOrCreate(secondPlayer);

        Match match = new Match(fPlayer,sPlayer);

        return onGoingMatches.put(match);
    }
}
