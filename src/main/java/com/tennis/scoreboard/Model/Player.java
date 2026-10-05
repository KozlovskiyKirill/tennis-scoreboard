package com.tennis.scoreboard.Model;
import java.util.regex.Pattern;


public record Player(String name) {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{1,10}$");


    public Player{
        if(name == null || !NAME_PATTERN.matcher(name).matches())
            throw new IllegalArgumentException("Incorrect name! It should contain only letters.Max length - 10");
    }
}
