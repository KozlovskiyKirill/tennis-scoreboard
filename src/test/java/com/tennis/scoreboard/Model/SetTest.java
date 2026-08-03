package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import lombok.experimental.Helper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetTest {
    // добавление законченных игр игроку

    private void finishGame(TennisSet set, PlayerSide winner) {
        for (int i = 0; i < 4; i++) {
            set.addPoints(winner);
        }
    }

    private void winNTimes(TennisSet set, PlayerSide winner, int count){
        for(int i = 0; i<count;i++){
            finishGame(set,winner);
        }
    }

    @Test
    void firstWinsOne(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,1);
        assertEquals(1, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void firstWinsTwo(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,2);
        assertEquals(2, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void firstWinsThree(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,3);
        assertEquals(3, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void firstWinsFour(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,4);
        assertEquals(4, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void firstWinsFive(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,5);
        assertEquals(3, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void firstWinsSix(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,6);
        assertEquals(3, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.FIRST,set.getWinner());
    }

    @Test
    void secondWinsOne(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,1);
        assertEquals(0, set.getFirstPlayerWin());
        assertEquals(1,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void secondWinsTwo(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,2);
        assertEquals(0, set.getFirstPlayerWin());
        assertEquals(2,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void secondWinsThree(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,3);
        assertEquals(0, set.getFirstPlayerWin());
        assertEquals(3,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void secondWinsFour(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,4);
        assertEquals(0, set.getFirstPlayerWin());
        assertEquals(4,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void secondWinsFive(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,5);
        assertEquals(0, set.getFirstPlayerWin());
        assertEquals(5,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void secondWinsSix(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,6);
        assertEquals(0, set.getFirstPlayerWin());
        assertEquals(6,set.getSecondPlayerWin());
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.SECOND,set.getWinner());
    }
    // определение победителя
    // / уверенная победа
    @Test
    void firstWinsWithDifference(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,3);
        winNTimes(set,PlayerSide.SECOND,3);
        winNTimes(set,PlayerSide.FIRST,3);
        assertEquals(6, set.getFirstPlayerWin());
        assertEquals(3,set.getSecondPlayerWin());
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.FIRST,set.getWinner());
    }

    @Test
    void secondWinsWithDifference(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,3);
        winNTimes(set,PlayerSide.FIRST,3);
        winNTimes(set,PlayerSide.SECOND,3);
        assertEquals(3, set.getFirstPlayerWin());
        assertEquals(6,set.getSecondPlayerWin());
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.SECOND,set.getWinner());
    }
    // / нужен tie-break
    @Test
    void five_six(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.SECOND,3);
        winNTimes(set,PlayerSide.FIRST,5);
        winNTimes(set,PlayerSide.SECOND,3);
        assertEquals(5, set.getFirstPlayerWin());
        assertEquals(6,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void six_five(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,3);
        winNTimes(set,PlayerSide.SECOND,5);
        winNTimes(set,PlayerSide.FIRST,3);
        assertEquals(6, set.getFirstPlayerWin());
        assertEquals(5,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }
    // ничья (tie-break)
    @Test
    void seven_five(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,3);
        winNTimes(set,PlayerSide.SECOND,5);
        winNTimes(set,PlayerSide.FIRST,3);
        winNTimes(set,PlayerSide.FIRST,1);
        assertEquals(7, set.getFirstPlayerWin());
        assertEquals(5,set.getSecondPlayerWin());
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.FIRST, set.getWinner());
    }

    @Test
    void five_seven(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,5);
        winNTimes(set,PlayerSide.SECOND,5);
        winNTimes(set,PlayerSide.SECOND,2);
        assertEquals(5, set.getFirstPlayerWin());
        assertEquals(7,set.getSecondPlayerWin());
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.SECOND, set.getWinner());
    }
    // при тай-бреке считать очки
}
