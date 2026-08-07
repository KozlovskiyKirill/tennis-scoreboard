package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Repository.MatchRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveMatch {
    private final OnGoingMatches onGoingMatches;
    private final MatchRepository matchRepository;

    public SaveMatch(OnGoingMatches onGoingMatches, MatchRepository matchRepository){
        this.onGoingMatches = onGoingMatches;
        this.matchRepository = matchRepository;
    }

    void saveMatch(Match match){
        if(match.isFinished()){
            onGoingMatches.delete(match);
            matchRepository.save(match);
        }
    }
}
