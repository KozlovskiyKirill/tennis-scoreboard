package com.tennis.scoreboard.DTO;

public record ScoreResponse(PlayerScore firstPlayer, PlayerScore secondPlayer, String winnerName) { }
