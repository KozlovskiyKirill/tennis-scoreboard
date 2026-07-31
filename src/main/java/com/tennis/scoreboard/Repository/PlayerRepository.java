package com.tennis.scoreboard.Repository;

import com.tennis.scoreboard.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    public Optional<Player> findByName(String name);
}
