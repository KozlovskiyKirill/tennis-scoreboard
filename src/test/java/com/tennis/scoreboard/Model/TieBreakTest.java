package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TieBreakTest {

    private void nPoints(TieBreak tieBreak, PlayerSide side, int n) {
        for (int i = 0; i < n; i++) {
            tieBreak.addPoints(side);
        }
    }

    /* ----------  INITIAL STATE ---------- */
    @Test
    void newTieBreakStartsAtZeroZero() {
        TieBreak tieBreak = new TieBreak();
        assertEquals(0, tieBreak.getPoints(PlayerSide.FIRST));
        assertEquals(0, tieBreak.getPoints(PlayerSide.SECOND));
        assertNull(tieBreak.getWinner());
        assertFalse(tieBreak.isFinished());
    }

    /* ----------  ПОБЕДА ---------- */
    @Test
    void sevenToZeroFinishes() {
        TieBreak tieBreak = new TieBreak();
        nPoints(tieBreak, PlayerSide.FIRST, 7);
        assertTrue(tieBreak.isFinished());
        assertEquals(PlayerSide.FIRST, tieBreak.getWinner());
        assertEquals(7, tieBreak.getPoints(PlayerSide.FIRST));
        assertEquals(0, tieBreak.getPoints(PlayerSide.SECOND));
    }

    @Test
    void firstWinsTieBreak() {
        TieBreak tieBreak = new TieBreak();
        nPoints(tieBreak, PlayerSide.SECOND, 5);
        nPoints(tieBreak, PlayerSide.FIRST, 7);
        assertTrue(tieBreak.isFinished());
        assertEquals(7, tieBreak.getPoints(PlayerSide.FIRST));
        assertEquals(5, tieBreak.getPoints(PlayerSide.SECOND));
        assertEquals(PlayerSide.FIRST, tieBreak.getWinner());
    }

    @Test
    void secondWinsTieBreak() {
        TieBreak tieBreak = new TieBreak();
        nPoints(tieBreak, PlayerSide.FIRST, 5);
        nPoints(tieBreak, PlayerSide.SECOND, 7);
        assertTrue(tieBreak.isFinished());
        assertEquals(5, tieBreak.getPoints(PlayerSide.FIRST));
        assertEquals(7, tieBreak.getPoints(PlayerSide.SECOND));
        assertEquals(PlayerSide.SECOND, tieBreak.getWinner());
    }

    /* ----------  ЕЩЁ НЕ ПОБЕДА ---------- */
    @Test
    void tieBreakSixSixNotFinished() {
        TieBreak tieBreak = new TieBreak();
        for (int i = 0; i < 6; i++) {
            tieBreak.addPoints(PlayerSide.FIRST);
            tieBreak.addPoints(PlayerSide.SECOND);
        }
        assertFalse(tieBreak.isFinished());
        assertEquals(6, tieBreak.getPoints(PlayerSide.FIRST));
        assertEquals(6, tieBreak.getPoints(PlayerSide.SECOND));
        assertNull(tieBreak.getWinner());
    }

    @Test
    void tieBreakSevenSixNotFinished() {
        TieBreak tieBreak = new TieBreak();
        for (int i = 0; i < 6; i++) {
            tieBreak.addPoints(PlayerSide.FIRST);
            tieBreak.addPoints(PlayerSide.SECOND);
        }
        tieBreak.addPoints(PlayerSide.FIRST); // 7-6
        assertFalse(tieBreak.isFinished());
        assertNull(tieBreak.getWinner());
    }

    @Test
    void tieBreakSevenFiveFinished() {
        TieBreak tieBreak = new TieBreak();
        for (int i = 0; i < 5; i++) {
            tieBreak.addPoints(PlayerSide.FIRST);
            tieBreak.addPoints(PlayerSide.SECOND);
        }
        tieBreak.addPoints(PlayerSide.FIRST);
        tieBreak.addPoints(PlayerSide.FIRST); // 7-5
        assertTrue(tieBreak.isFinished());
        assertEquals(PlayerSide.FIRST, tieBreak.getWinner());
    }

    @Test
    void firstWinsTieBreakAfterDeuce() {
        TieBreak tieBreak = new TieBreak();
        for (int i = 0; i < 6; i++) {
            tieBreak.addPoints(PlayerSide.FIRST);
            tieBreak.addPoints(PlayerSide.SECOND);
        }
        tieBreak.addPoints(PlayerSide.FIRST); // 7-6
        tieBreak.addPoints(PlayerSide.FIRST); // 8-6
        assertTrue(tieBreak.isFinished());
        assertEquals(8, tieBreak.getPoints(PlayerSide.FIRST));
        assertEquals(6, tieBreak.getPoints(PlayerSide.SECOND));
        assertEquals(PlayerSide.FIRST, tieBreak.getWinner());
    }

    /* ----------  ОЧКИ ПОСЛЕ ОКОНЧАНИЯ ---------- */
    @Test
    void tieBreakPointsIgnoredAfterFinished() {
        TieBreak tieBreak = new TieBreak();
        nPoints(tieBreak, PlayerSide.FIRST, 5);
        nPoints(tieBreak, PlayerSide.SECOND, 7);
        assertTrue(tieBreak.isFinished());
        assertEquals(PlayerSide.SECOND, tieBreak.getWinner());

        tieBreak.addPoints(PlayerSide.FIRST);
        tieBreak.addPoints(PlayerSide.FIRST);

        assertEquals(PlayerSide.SECOND, tieBreak.getWinner());
        assertEquals(7, tieBreak.getPoints(PlayerSide.SECOND));
        assertTrue(tieBreak.isFinished());
    }
}
