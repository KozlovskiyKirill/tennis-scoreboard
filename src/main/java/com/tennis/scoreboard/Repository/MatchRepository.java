package com.tennis.scoreboard.Repository;

import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MatchRepository extends JpaRepository <Match, UUID> { }
