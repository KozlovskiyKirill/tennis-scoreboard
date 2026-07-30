package com.tennis.scoreboard.Repository;

import com.tennis.scoreboard.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
    public Optional<Player> findByName(String name);
}
