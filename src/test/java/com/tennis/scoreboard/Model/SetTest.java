package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetTest {

    /* ---------- HELPERS ---------- */
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

    private void reachSixSix(TennisSet set){
        for(int i = 0; i<5;i++){
            finishGame(set,PlayerSide.FIRST);
            finishGame(set,PlayerSide.SECOND);
        }
        finishGame(set,PlayerSide.FIRST);    // 6-5
        finishGame(set,PlayerSide.SECOND);   // 6-6 -> tieBreak=true
    }

    private void playTieBreak(TennisSet set, PlayerSide winner, int loserTieBreakPoints){
        for(int i = 0; i<loserTieBreakPoints;i++){
            set.addPoints(PlayerSide.FIRST);
            set.addPoints(PlayerSide.SECOND);
        }
        int winnerPoints = Math.max(loserTieBreakPoints + 2, 7);
        for(int i = 0; i<winnerPoints - loserTieBreakPoints;i++){
            set.addPoints(winner);
        }
    }

    /* ---------- ПРОСТОЙ ВЫИГРЫШ ПЕРВОГО ИГРОКА (1-6 ИГР) ---------- */
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
        assertEquals(5, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    @Test
    void firstWinsSix(){
        TennisSet set = new TennisSet();
        winNTimes(set,PlayerSide.FIRST,6);
        assertEquals(6, set.getFirstPlayerWin());
        assertEquals(0,set.getSecondPlayerWin());
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.FIRST,set.getWinner());
    }

    /* ---------- ПРОСТОЙ ВЫИГРЫШ ВТОРОГО ИГРОКА (1-6 ИГР) ---------- */
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

    /* ---------- ОПРЕДЕЛЕНИЕ ПОБЕДИТЕЛЯ ---------- */
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

    /* ---------- СЧИТЫВАНИЕ НИЧЬИ (6-6) ---------- */
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

    /* ---------- ТАЙ-БРЕЙК ---------- */
    @Test
    void regularGameBeforeSixSix(){
        TennisSet set = new TennisSet();
        set.addPoints(PlayerSide.FIRST);
        assertNotNull(set.getCurrentGame());
        assertTrue(set.getCurrentGame() instanceof Game, "до 6-6 сет должен создавать простую игру");
        assertFalse(set.isFinished());
    }

    @Test
    void tieBreakGameAfterSixSix(){
        TennisSet set = new TennisSet();
        reachSixSix(set);
        set.addPoints(PlayerSide.FIRST);
        assertNotNull(set.getCurrentGame());
        assertTrue(set.getCurrentGame() instanceof TieBreak, "после 6-6 сет должен создавать тай-брейк");
        assertFalse(set.isFinished());
    }

    @Test
    void getScoreIsNullDuringTieBreak(){
        TennisSet set = new TennisSet();
        reachSixSix(set);
        set.addPoints(PlayerSide.FIRST);
        assertTrue(set.getCurrentGame() instanceof TieBreak);
        assertNull(set.getScore(PlayerSide.FIRST), "во время тай-брейка обычный счёт отсутствует");
        assertNull(set.getScore(PlayerSide.SECOND));
    }

    @Test
    void sixSixIsNotFinished(){
        TennisSet set = new TennisSet();
        reachSixSix(set);
        assertFalse(set.isFinished());
        assertNull(set.getWinner());
    }

    /* ---------- ПОБЕДА ЧЕРЕЗ ТАЙ-БРЕЙК ---------- */
    @Test
    void firstWinsSetAfterTieBreak(){
        TennisSet set = new TennisSet();
        reachSixSix(set);
        playTieBreak(set, PlayerSide.FIRST, 5);
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.FIRST, set.getWinner());
        assertEquals(7, set.getFirstPlayerWin());
        assertEquals(6, set.getSecondPlayerWin());
    }

    @Test
    void secondWinsSetAfterTieBreak(){
        TennisSet set = new TennisSet();
        reachSixSix(set);
        playTieBreak(set, PlayerSide.SECOND, 5);
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.SECOND, set.getWinner());
        assertEquals(6, set.getFirstPlayerWin());
        assertEquals(7, set.getSecondPlayerWin());
    }

    @Test
    void tieBreakGoesToEightSixButSetIsSevenSix(){
        TennisSet set = new TennisSet();
        reachSixSix(set);
        playTieBreak(set, PlayerSide.FIRST, 6);
        assertTrue(set.isFinished());
        assertEquals(PlayerSide.FIRST, set.getWinner());
        assertEquals(7, set.getFirstPlayerWin());
        assertEquals(6, set.getSecondPlayerWin());
    }

    /* ---------- ОЧКИ ПОСЛЕ ОКОНЧАНИЯ СЕТА ---------- */
    @Test
    void pointsIgnoredAfterSetFinishedByTieBreak(){
        TennisSet set = new TennisSet();
        reachSixSix(set);
        playTieBreak(set, PlayerSide.FIRST, 5);
        assertTrue(set.isFinished());

        set.addPoints(PlayerSide.FIRST);
        set.addPoints(PlayerSide.SECOND);

        assertEquals(PlayerSide.FIRST, set.getWinner());
        assertTrue(set.isFinished());
    }
}
