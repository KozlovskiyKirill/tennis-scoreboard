package com.tennis.scoreboard.Controller;


import com.tennis.scoreboard.DTO.*;
import com.tennis.scoreboard.Exception.MatchNotFoundException;
import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Service.CalculateScoreService;
import com.tennis.scoreboard.Service.MatchService;
import com.tennis.scoreboard.Service.OnGoingMatches;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tennis.scoreboard.Service.SaveMatch;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping ("/matches")
public class MatchController {

    private final MatchService _matchService;
    private final CalculateScoreService _scoreService;
    private final OnGoingMatches _onGoingMatches;

    public MatchController(MatchService matchService, CalculateScoreService scoreService,
                           OnGoingMatches onGoingMatches){
        _matchService = matchService;
        _scoreService = scoreService;
        _onGoingMatches = onGoingMatches;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateMatchRequest request){
        UUID code = _matchService.createMatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",code));
    }

    @PostMapping("/{uuid}/point")
    public ResponseEntity<?> addPoints(@PathVariable UUID uuid, @RequestBody AddPointsToPlayer request){
        String name = request.name();
        ScoreResponse response = _scoreService.addPoints(uuid,name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> receiveStatistics(@PathVariable UUID uuid){
        Match match = _onGoingMatches.find(uuid);
        ScoreResponse response = _scoreService.receiveMatchStatistics(match);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<?> finishedMatches(
            @RequestParam(required = false, name = "player_name") String playerName,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ){
        MatchPage matches;
        Pageable pageable = PageRequest.of(page - 1, 10);
        if (playerName==null) {
            matches = _matchService.receiveFinishedMatches(pageable);

        }
        else {
            matches = _matchService.receiveFinishedMatchesByName(playerName, pageable);
        }
        return ResponseEntity.ok(matches);
    }

}

