package com.tennis.scoreboard.Repository;

import com.tennis.scoreboard.Model.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository <Match, Integer> {
    @Query("SELECT m FROM Match m WHERE m._player1.name=:name OR m._player2.name=:name")
    Page<Match> findByName(@Param("name") String name, Pageable pageable);
    Page<Match> findAll(Pageable pageable);
}
