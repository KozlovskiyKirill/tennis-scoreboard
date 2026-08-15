package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /* ---------- HELPERS ---------- */
    private Game regularGame() {
        return new Game();
    }

    private void nPoints(Game game, PlayerSide side, int n) {
        for (int i = 0; i < n; i++) {
            game.addPoints(side);
        }
    }

    private void bringToDeuce(Game game) {
        nPoints(game, PlayerSide.FIRST, 3);
        nPoints(game, PlayerSide.SECOND, 3);
    }

    /* ----------  INITIAL STATE ---------- */
    @Test
    void newGameStartsAtLoveLove() {
        Game game = regularGame();
        assertEquals(Score.LOVE, game.getFirstPlayer());
        assertEquals(Score.LOVE, game.getSecondPlayer());
        assertNull(game.getWinner());
        assertFalse(game.isFinished());
    }

    /* ----------  getScore(PlayerSide) ---------- */
    @Test
    void getScoreReturnsCorrectPlayer() {
        Game game = regularGame();
        nPoints(game, PlayerSide.FIRST, 2);
        assertEquals(Score.THIRTY, game.getScore(PlayerSide.FIRST));
        assertEquals(Score.LOVE, game.getScore(PlayerSide.SECOND));
    }

    /* ---------- ДОБАВЛЕНИЕ ОЧКОВ ---------- */
    @Test
    void firstPointShouldBeFifteen() {
        Game game = regularGame();
        game.addPoints(PlayerSide.FIRST);
        assertEquals(Score.FIFTEEN, game.getFirstPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void secondPointShouldBeThirty() {
        Game game = regularGame();
        nPoints(game, PlayerSide.FIRST, 2);
        assertEquals(Score.THIRTY, game.getFirstPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void thirdPointShouldBeForty() {
        Game game = regularGame();
        nPoints(game, PlayerSide.FIRST, 3);
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void firstPointShouldBeFifteen_second() {
        Game game = regularGame();
        game.addPoints(PlayerSide.SECOND);
        assertEquals(Score.FIFTEEN, game.getSecondPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void secondPointShouldBeThirty_second() {
        Game game = regularGame();
        nPoints(game, PlayerSide.SECOND, 2);
        assertEquals(Score.THIRTY, game.getSecondPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void thirdPointShouldBeForty_second() {
        Game game = regularGame();
        nPoints(game, PlayerSide.SECOND, 3);
        assertEquals(Score.FORTY, game.getSecondPlayer());
        assertNull(game.getWinner());
    }

    /* ----------  ЯВНАЯ ПОБЕДА (40 против ниже) ---------- */
    @Test
    void Forty_Plus_Love() {
        Game game = regularGame();
        nPoints(game, PlayerSide.FIRST, 4);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.LOVE, game.getSecondPlayer());
    }

    @Test
    void Love_Forty_Plus() {
        Game game = regularGame();
        nPoints(game, PlayerSide.SECOND, 4);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
        assertEquals(Score.LOVE, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
    }

    @Test
    void Forty_Plus_Fifteen() {
        Game game = regularGame();
        nPoints(game, PlayerSide.FIRST, 3);
        nPoints(game, PlayerSide.SECOND, 1);
        game.addPoints(PlayerSide.FIRST);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void Fifteen_Forty_Plus() {
        Game game = regularGame();
        nPoints(game, PlayerSide.SECOND, 3);
        nPoints(game, PlayerSide.FIRST, 1);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }

    @Test
    void Forty_Plus_Thirty() {
        Game game = regularGame();
        nPoints(game, PlayerSide.FIRST, 3);
        nPoints(game, PlayerSide.SECOND, 2);
        game.addPoints(PlayerSide.FIRST);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void Thirty_Forty_Plus() {
        Game game = regularGame();
        nPoints(game, PlayerSide.SECOND, 3);
        nPoints(game, PlayerSide.FIRST, 2);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }

    /* ----------  ДЕУС (40-40) ---------- */
    @Test
    void Forty_Forty() {
        Game game = regularGame();
        bringToDeuce(game);
        assertFalse(game.isFinished());
        assertNull(game.getWinner());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
    }

    /* ----------  AD (после деуса) ---------- */
    @Test
    void FirstGetsAdvantage() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.FIRST);
        assertFalse(game.isFinished());
        assertNull(game.getWinner());
        assertEquals(Score.AD, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
    }

    @Test
    void SecondGetsAdvantage() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.SECOND);
        assertFalse(game.isFinished());
        assertNull(game.getWinner());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.AD, game.getSecondPlayer());
    }

    @Test
    void AD_Lost_ReturnsToDeuce() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.FIRST);   // FIRST -> AD
        game.addPoints(PlayerSide.SECOND);  // теряет AD -> 40-40
        assertFalse(game.isFinished());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
        assertNull(game.getWinner());
    }

    /* ----------  AD -> ПОБЕДА ---------- */
    @Test
    void AD_Plus_Win() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.FIRST);   // AD
        game.addPoints(PlayerSide.FIRST);   // WIN
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void AD_Plus_Win_second() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }

    /* ---------- F. ДЕУС КАЧАЛКА ---------- */
    @Test
    void DeuceOscillationFirstWins() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.FIRST);   // AD
        game.addPoints(PlayerSide.SECOND);  // 40-40
        game.addPoints(PlayerSide.FIRST);   // AD
        game.addPoints(PlayerSide.SECOND);  // 40-40
        game.addPoints(PlayerSide.FIRST);   // AD
        game.addPoints(PlayerSide.FIRST);   // WIN
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void LongDeuceNoWinner() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        assertFalse(game.isFinished());
        assertNull(game.getWinner());
    }

    @Test
    void First_wins() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.FIRST);   // AD
        game.addPoints(PlayerSide.SECOND);  // 40-40
        game.addPoints(PlayerSide.FIRST);   // AD
        game.addPoints(PlayerSide.FIRST);   // WIN
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());
    }

    @Test
    void Second_wins() {
        Game game = regularGame();
        bringToDeuce(game);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND, game.getWinner());
    }

    /* ----------  ОЧКИ ПОСЛЕ ОКОНЧАНИЯ ИГРЫ ---------- */
    @Test
    void pointsIgnoredAfterGameFinished() {
        Game game = regularGame();
        nPoints(game, PlayerSide.FIRST, 4);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST, game.getWinner());

        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);

        assertEquals(PlayerSide.FIRST, game.getWinner());
        assertTrue(game.isFinished());
    }
    /* ----------  НЕЗАВИСИМОСТЬ ОБЪЕКТОВ ---------- */
    @Test
    void twoGamesAreIndependent() {
        Game g1 = regularGame();
        Game g2 = regularGame();
        nPoints(g1, PlayerSide.FIRST, 4);
        assertEquals(Score.LOVE, g2.getFirstPlayer());
        assertEquals(Score.LOVE, g2.getSecondPlayer());
        assertNull(g2.getWinner());
    }
}
