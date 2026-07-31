package com.tennis.scoreboard.Controller;


import com.tennis.scoreboard.DTO.AddPointsToPlayer;
import com.tennis.scoreboard.DTO.CreateMatchRequest;
import com.tennis.scoreboard.DTO.ScoreResponse;
import com.tennis.scoreboard.Service.CalculateScoreService;
import com.tennis.scoreboard.Service.MatchService;

import java.util.Map;
import java.util.UUID;

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

    public MatchController(MatchService matchService, CalculateScoreService scoreService){
        _matchService = matchService;
        _scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateMatchRequest request){
        UUID code = _matchService.createMatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",code));
    }

    @PostMapping("/{uuid}/point")
    public ResponseEntity<?> addPoints(@PathVariable UUID uuid, @RequestBody AddPointsToPlayer request){
        String name = request.Player();
        ScoreResponse response = _scoreService.addPoints(uuid,name);
        return ResponseEntity.ok(response);
    }

}
