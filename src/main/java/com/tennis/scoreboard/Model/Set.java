package com.tennis.scoreboard.Model;

public class Set {
    private Game currentGame;
    private int gameCount = 0;

    private int firstPlayerWin = 0;
    private int secondPlayerWin = 0;


    void addPoints(String player){
        // проверка на счет и т.д.
        if(currentGame==null){
            currentGame = new Game();
            currentGame.addPoints();
        }
    }
}
