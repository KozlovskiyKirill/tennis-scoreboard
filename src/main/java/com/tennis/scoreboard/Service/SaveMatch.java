package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.Entity.MatchEntity;
import com.tennis.scoreboard.Entity.PlayerEntity;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Repository.MatchRepository;
import com.tennis.scoreboard.Repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    void saveMatch(UUID uuid, Match match){
        if(match.isFinished()){
            onGoingMatches.delete(uuid);
            PlayerEntity fplayer = playerRepository.findByName(match.get_player1().name()).
                    orElseThrow(()->new IllegalArgumentException("Игрок не найден в базе данных"));
            PlayerEntity splayer = playerRepository.findByName(match.get_player2().name()).
                    orElseThrow(()->new IllegalArgumentException("Игрок не найден в базе данных"));
            PlayerEntity winner = playerRepository.findByName(match.getWinner().name()).
                    orElseThrow(()->new IllegalArgumentException("Игрок не найден в базе данных"));

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
