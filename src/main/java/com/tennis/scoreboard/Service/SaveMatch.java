package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.Entity.MatchEntity;
import com.tennis.scoreboard.Entity.PlayerEntity;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Repository.MatchRepository;
import com.tennis.scoreboard.Repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaveMatch {
    private final OnGoingMatches onGoingMatches;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public SaveMatch(OnGoingMatches onGoingMatches, MatchRepository matchRepository, PlayerRepository playerRepository){
        this.onGoingMatches = onGoingMatches;
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    void saveMatch(Match match){
        if(match.isFinished()){
            onGoingMatches.delete(match);
            PlayerEntity fplayer = playerRepository.findByName(match.get_player1().name()).orElseThrow();
            PlayerEntity splayer = playerRepository.findByName(match.get_player2().name()).orElseThrow();
            PlayerEntity winner = playerRepository.findByName(match.getWinner().name()).orElseThrow();

            if (!winner.equals(fplayer) && !winner.equals(splayer)) {
                throw new IllegalArgumentException("Winner is not one of the players");
            }
            if (fplayer.equals(splayer)) {
                throw new IllegalArgumentException("Players must be different");
            }
            matchRepository.save(new MatchEntity(fplayer, splayer,winner));
        }
    }
}
