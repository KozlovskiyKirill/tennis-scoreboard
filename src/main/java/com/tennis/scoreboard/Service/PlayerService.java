package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.Model.Player;
import com.tennis.scoreboard.Repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public Player findOrCreate(String name){
        return playerRepository.findByName(name).orElseGet(()->playerRepository.save(new Player(name)));
    }
}
