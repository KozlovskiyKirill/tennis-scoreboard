package com.tennis.scoreboard.DTO;
import com.tennis.scoreboard.Model.Match;

import java.util.List;

public record MatchPage(List<FinishedMatch> matches, int currentPage, int totalPages) { }
