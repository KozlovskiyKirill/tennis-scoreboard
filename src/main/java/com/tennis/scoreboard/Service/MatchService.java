package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.DTO.CreateMatchRequest;
import com.tennis.scoreboard.DTO.FinishedMatch;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Model.Player;
import com.tennis.scoreboard.Repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {
    private final PlayerService playerService;
    private final OnGoingMatches onGoingMatches;
    private final MatchRepository matchRepository;

    public MatchService(PlayerService playerService, OnGoingMatches onGoingMatches, MatchRepository matchRepository){
        this.playerService = playerService;
        this.onGoingMatches = onGoingMatches;
        this.matchRepository = matchRepository;
    }

    public UUID createMatch(CreateMatchRequest response){
        String firstPlayer = response.firstPlayer();
        String secondPlayer = response.secondPlayer();

        Player fPlayer = playerService.findOrCreate(firstPlayer);
        Player sPlayer = playerService.findOrCreate(secondPlayer);

        Match match = new Match(fPlayer,sPlayer);

        return onGoingMatches.put(match);
    }

    public List<FinishedMatch> receiveFinishedMatches(){
        List<Match> matches= matchRepository.findAll();
        List<FinishedMatch> finalMatches = new ArrayList<>();
        for(Match match :matches){
            finalMatches.add(new FinishedMatch(match.get_player1().getName(),match.get_player2().getName(),
                    match.getWinner().getName()));
        }
        return finalMatches;
    }
}
