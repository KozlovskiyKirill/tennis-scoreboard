package com.tennis.scoreboard.Repository;

import com.tennis.scoreboard.Model.Match;
import com.tennis.scoreboard.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface MatchRepository extends JpaRepository <Match, Integer> {
    @Query("SELECT m FROM Match m WHERE m._player1.name=:name OR m._player2.name=:name")
    List<Match> findByName(@Param("name") String name);
}
