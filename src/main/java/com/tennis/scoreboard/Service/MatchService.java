package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.DTO.CreateMatchRequest;
import com.tennis.scoreboard.DTO.FinishedMatch;
import com.tennis.scoreboard.DTO.MatchPage;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Model.Player;
import com.tennis.scoreboard.Repository.MatchRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public MatchPage receiveFinishedMatches(Pageable pageable){
        Page<Match> matches= matchRepository.findAll(pageable);
        List<FinishedMatch> finalMatches = matches.getContent().stream().map(m->
                new FinishedMatch(m.get_player1().getName(),m.get_player2().getName(),m.getWinner().getName())).
                toList();
        return new MatchPage(finalMatches,matches.getNumber(),matches.getTotalPages());
    }

    public MatchPage receiveFinishedMatchesByName(String name, Pageable pageable){
        Page<Match> matches =matchRepository.findByName(name, pageable);
        List<FinishedMatch> finalMatches = matches.getContent().stream().map(m->
                new FinishedMatch(m.get_player1().getName(),m.get_player2().getName(),m.getWinner().getName()))
                .toList();
        return new MatchPage(finalMatches,matches.getNumber(),matches.getTotalPages());

    }
}
