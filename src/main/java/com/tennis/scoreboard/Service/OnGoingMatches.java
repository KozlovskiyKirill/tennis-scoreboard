package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.Exception.MatchNotFoundException;
import com.tennis.scoreboard.Model.Match;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OnGoingMatches {

    Map<UUID, Match> matches = new HashMap<>();

    UUID put(Match match){
        if(match==null) throw new MatchNotFoundException("Матч не может быть добавлен в текущие");
        UUID uuid = UUID.randomUUID();
        matches.put(uuid,match);
        return uuid;
    }

    public Match find(UUID uuid){
        return matches.getOrDefault(uuid, null);
    }
    void delete(Match match) {matches.remove(match);}
}
