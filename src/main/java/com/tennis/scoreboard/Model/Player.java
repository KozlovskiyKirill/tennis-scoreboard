package com.tennis.scoreboard.Model;

public record Player(String name) {
    public Player{
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Player name must not be blank");
        if(name.length()>10)
            throw new IllegalArgumentException("Name is too long");
    }
}
