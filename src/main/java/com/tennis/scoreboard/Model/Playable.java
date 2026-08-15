package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;

public interface Playable {

    void addPoints(PlayerSide player);

    boolean isFinished();

    PlayerSide getWinner();
}
