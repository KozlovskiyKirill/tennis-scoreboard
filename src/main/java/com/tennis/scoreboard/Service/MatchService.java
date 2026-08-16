package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.DTO.CreateMatchRequest;
import com.tennis.scoreboard.DTO.FinishedMatch;
import com.tennis.scoreboard.DTO.MatchPage;
import com.tennis.scoreboard.Entity.MatchEntity;
import com.tennis.scoreboard.Entity.PlayerEntity;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Model.Player;
import com.tennis.scoreboard.Repository.MatchRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        String firstPlayer = response.firstPlayerName();
        String secondPlayer = response.secondPlayerName();

        checkPlayersNames(firstPlayer, secondPlayer);

        PlayerEntity fPlayer = playerService.findOrCreate(firstPlayer);
        PlayerEntity sPlayer = playerService.findOrCreate(secondPlayer);

        Match match = new Match(new Player(fPlayer.getName()),new Player(sPlayer.getName()));

        return onGoingMatches.put(match);
    }
    private void checkPlayersNames(String firstPlayer, String secondPlayer){
        if (firstPlayer == null || firstPlayer.isBlank())
            throw new IllegalArgumentException("Player 1 name must not be blank");
        if(firstPlayer.length()>10)
            throw new IllegalArgumentException("Player name 1 is too long");
        if (secondPlayer == null || secondPlayer.isBlank())
            throw new IllegalArgumentException("Player 2 name must not be blank");
        if(secondPlayer.length()>10)
            throw new IllegalArgumentException("Player name 2 is too long");
        if (firstPlayer.equals(secondPlayer)) throw new IllegalArgumentException("Players has the same names");
    }

    public MatchPage receiveFinishedMatches(Pageable pageable){
        return toMatchPage(matchRepository.findAll(pageable));
    }

    public MatchPage receiveFinishedMatchesByName(String name, Pageable pageable){
        return toMatchPage(matchRepository.findByName(name, pageable));
    }

    private MatchPage toMatchPage(Page<MatchEntity> matches){
        List<FinishedMatch> finalMatches = matches.getContent().stream().map(m->
                new FinishedMatch(m.get_player1().getName(),m.get_player2().getName(),m.get_winner().getName()))
                .toList();
        return new MatchPage(finalMatches,matches.getNumber() + 1,matches.getTotalPages());
    }
}
