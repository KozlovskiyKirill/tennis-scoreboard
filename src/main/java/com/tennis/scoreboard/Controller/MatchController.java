package com.tennis.scoreboard.Controller;


import com.tennis.scoreboard.DTO.CreateMatchRequest;
import com.tennis.scoreboard.Service.MatchService;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping ("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService){
        this.matchService = matchService;
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateMatchRequest request){
        UUID code = matchService.createMatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",code));
    }
}
