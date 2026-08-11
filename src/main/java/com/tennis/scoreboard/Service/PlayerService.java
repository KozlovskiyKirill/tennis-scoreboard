package com.tennis.scoreboard.Service;

import com.tennis.scoreboard.Entity.PlayerEntity;
import com.tennis.scoreboard.Repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public PlayerEntity findOrCreate(String name){
        return playerRepository.findByName(name).orElseGet(()->playerRepository.save(new PlayerEntity(name)));
    }
}
