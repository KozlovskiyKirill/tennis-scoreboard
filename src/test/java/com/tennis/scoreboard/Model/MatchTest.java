package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    /* ---------- HELPERS ---------- */
    private Match createMatch() {
        return new Match(new Player("A"), new Player("B"));
    }

    private void finishGame(Match match, PlayerSide winner) {
        for (int i = 0; i < 4; i++) {
            match.addPoints(winner);
        }
    }

    private void winSet(Match match, PlayerSide winner) {
        for (int i = 0; i < 6; i++) {
            finishGame(match, winner);
        }
    }

    private void winTwoSetsInRow(Match match, PlayerSide winner) {
        winSet(match, winner);
        winSet(match, winner);
    }

    private void reachSixSix(Match match) {
        for (int i = 0; i < 5; i++) {
            finishGame(match, PlayerSide.FIRST);
            finishGame(match, PlayerSide.SECOND);
        }
        finishGame(match, PlayerSide.FIRST);    // 6-5
        finishGame(match, PlayerSide.SECOND);   // 6-6
    }

    private void playTieBreak(Match match, PlayerSide winner, int loserTieBreakPoints) {
        for (int i = 0; i < loserTieBreakPoints; i++) {
            match.addPoints(PlayerSide.FIRST);
            match.addPoints(PlayerSide.SECOND);
        }
        int winnerPoints = Math.max(loserTieBreakPoints + 2, 7);
        for (int i = 0; i < winnerPoints - loserTieBreakPoints; i++) {
            match.addPoints(winner);
        }
    }

    private void winSetWithTieBreak(Match match, PlayerSide winner) {
        reachSixSix(match);
        playTieBreak(match, winner, 5);
    }

    /* ---------- НАЧАЛЬНОЕ СОСТОЯНИЕ ---------- */
    @Test
    void freshMatchHasDefaultState() {
        Match match = createMatch();
        assertFalse(match.isFinished());
        assertNull(match.getWinner());
        assertEquals(0, match.getSets(PlayerSide.FIRST));
        assertEquals(0, match.getSets(PlayerSide.SECOND));
    }

    /* ---------- ГЕТТЕРЫ: ОЧКИ ---------- */
    @Test
    void scoreAfterFirstPlayerPoint() {
        Match match = createMatch();
        match.addPoints(PlayerSide.FIRST);
        assertEquals(Score.FIFTEEN, match.getScore(PlayerSide.FIRST));
        assertEquals(Score.LOVE, match.getScore(PlayerSide.SECOND));
    }

    @Test
    void scoreAfterSecondPlayerPoint() {
        Match match = createMatch();
        match.addPoints(PlayerSide.SECOND);
        assertEquals(Score.LOVE, match.getScore(PlayerSide.FIRST));
        assertEquals(Score.FIFTEEN, match.getScore(PlayerSide.SECOND));
    }

    /* ---------- ГЕТТЕРЫ: ИГРЫ ---------- */
    @Test
    void gamesAfterFirstPlayerWinsOne() {
        Match match = createMatch();
        finishGame(match, PlayerSide.FIRST);
        assertEquals(1, match.getGames(PlayerSide.FIRST));
        assertEquals(0, match.getGames(PlayerSide.SECOND));
    }

    @Test
    void gamesAfterSecondPlayerWinsOne() {
        Match match = createMatch();
        finishGame(match, PlayerSide.SECOND);
        assertEquals(0, match.getGames(PlayerSide.FIRST));
        assertEquals(1, match.getGames(PlayerSide.SECOND));
    }

    /* ---------- ГЕТТЕРЫ: СЕТЫ ---------- */
    @Test
    void setsAfterFirstPlayerWinsOne() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(0, match.getSets(PlayerSide.SECOND));
    }

    @Test
    void setsAfterSecondPlayerWinsOne() {
        Match match = createMatch();
        winSet(match, PlayerSide.SECOND);
        assertEquals(0, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));
    }

    /* ---------- ЛОГИКА МАТЧА: ВЫИГРЫШ СЕТОВ ---------- */
    @Test
    void firstPlayerWinsSet_scoreIncreases() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(0, match.getSets(PlayerSide.SECOND));
        assertFalse(match.isFinished());
    }

    @Test
    void secondPlayerWinsSet_scoreIncreases() {
        Match match = createMatch();
        winSet(match, PlayerSide.SECOND);
        assertEquals(0, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));
        assertFalse(match.isFinished());
    }

    /* ---------- ПОБЕДА В МАТЧЕ (ПЕРВЫЙ ДО 2 СЕТОВ) ---------- */
    @Test
    void firstPlayerWinsTwoSetsInRow_winsMatch() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);
        winSet(match, PlayerSide.FIRST);
        assertEquals(2, match.getSets(PlayerSide.FIRST));
        assertEquals(0, match.getSets(PlayerSide.SECOND));
        assertTrue(match.isFinished());
    }

    @Test
    void secondPlayerWinsTwoSetsInRow_winsMatch() {
        Match match = createMatch();
        winSet(match, PlayerSide.SECOND);
        winSet(match, PlayerSide.SECOND);
        assertEquals(0, match.getSets(PlayerSide.FIRST));
        assertEquals(2, match.getSets(PlayerSide.SECOND));
        assertTrue(match.isFinished());
    }

    /* ---------- ХОД МАТЧА: ЧЕРЕДОВАНИЕ СЕТОВ ---------- */
    @Test
    void matchProgression_1_0_1_1_2_1() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);   // 1:0
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(0, match.getSets(PlayerSide.SECOND));

        winSet(match, PlayerSide.SECOND);  // 1:1
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));

        winSet(match, PlayerSide.FIRST);   // 2:1
        assertEquals(2, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));

        assertTrue(match.isFinished());
        assertEquals(match.get_player1(), match.getWinner());
    }

    @Test
    void matchProgression_0_1_1_1_1_2() {
        Match match = createMatch();
        winSet(match, PlayerSide.SECOND);  // 0:1
        assertEquals(0, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));

        winSet(match, PlayerSide.FIRST);   // 1:1
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));

        winSet(match, PlayerSide.SECOND);  // 1:2
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(2, match.getSets(PlayerSide.SECOND));

        assertTrue(match.isFinished());
        assertEquals(match.get_player2(), match.getWinner());
    }

    /* ---------- НЕЗАВИСИМОСТЬ СЕТОВ ---------- */
    @Test
    void eachSetWorksIndependently() {
        Match match = createMatch();

        // Сет 1: первый игрок выигрывает 6-4
        finishGame(match, PlayerSide.FIRST);  // 1-0
        finishGame(match, PlayerSide.FIRST);  // 2-0
        finishGame(match, PlayerSide.FIRST);  // 3-0
        finishGame(match, PlayerSide.FIRST);  // 4-0
        finishGame(match, PlayerSide.FIRST);  // 5-0
        finishGame(match, PlayerSide.SECOND); // 5-1
        finishGame(match, PlayerSide.SECOND); // 5-2
        finishGame(match, PlayerSide.SECOND); // 5-3
        finishGame(match, PlayerSide.SECOND); // 5-4
        finishGame(match, PlayerSide.FIRST);  // 6-4 сет завершен
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(0, match.getSets(PlayerSide.SECOND));

        // Сет 2: второй игрок выигрывает 6-3
        finishGame(match, PlayerSide.SECOND); // 1-0
        finishGame(match, PlayerSide.SECOND); // 2-0
        finishGame(match, PlayerSide.SECOND); // 3-0
        finishGame(match, PlayerSide.SECOND); // 4-0
        finishGame(match, PlayerSide.SECOND); // 5-0
        finishGame(match, PlayerSide.FIRST);  // 5-1
        finishGame(match, PlayerSide.FIRST);  // 5-2
        finishGame(match, PlayerSide.FIRST);  // 5-3
        finishGame(match, PlayerSide.SECOND); // 6-3 сет завершен
        assertEquals(1, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));

        // Сет 3: первый игрок выигрывает 6-2
        finishGame(match, PlayerSide.FIRST);  // 1-0
        finishGame(match, PlayerSide.FIRST);  // 2-0
        finishGame(match, PlayerSide.FIRST);  // 3-0
        finishGame(match, PlayerSide.FIRST);  // 4-0
        finishGame(match, PlayerSide.SECOND); // 4-1
        finishGame(match, PlayerSide.SECOND); // 4-2
        finishGame(match, PlayerSide.FIRST);  // 5-2
        finishGame(match, PlayerSide.FIRST);  // 6-2 сет завершен
        assertEquals(2, match.getSets(PlayerSide.FIRST));
        assertEquals(1, match.getSets(PlayerSide.SECOND));

        assertTrue(match.isFinished());
        assertEquals(match.get_player1(), match.getWinner());
    }

    /* ---------- ОЧКИ ПОСЛЕ ОКОНЧАНИЯ МАТЧА ---------- */
    @Test
    void pointsIgnoredAfterMatchFinished() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);
        winSet(match, PlayerSide.FIRST);
        assertTrue(match.isFinished());

        match.addPoints(PlayerSide.SECOND);
        match.addPoints(PlayerSide.SECOND);

        assertEquals(match.get_player1(), match.getWinner());
        assertTrue(match.isFinished());
    }

    /* ---------- МАТЧ С ТАЙ-БРЕЙКОМ ---------- */
    @Test
    void matchWithTieBreakSet() {
        Match match = createMatch();
        winSetWithTieBreak(match, PlayerSide.FIRST);  // 1:0
        winSetWithTieBreak(match, PlayerSide.SECOND); // 1:1
        winSetWithTieBreak(match, PlayerSide.FIRST);  // 2:1
        assertTrue(match.isFinished());
        assertEquals(match.get_player1(), match.getWinner());
    }

    /* ---------- МАТЧ НЕ ЗАКОНЧЕН ПОСЛЕ 2 СЕТОВ ---------- */
    @Test
    void matchNotFinishedAfterTwoSets() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);
        winSet(match, PlayerSide.SECOND);
        assertFalse(match.isFinished());
        assertNull(match.getWinner());
    }

    /* ---------- ГЕТТЕРЫ: ДЕФОЛТНЫЕ ЗНАЧЕНИЯ БЕЗ ТЕКУЩЕГО СЕТА ---------- */
    @Test
    void getScoreReturnsLoveWhenNoPointsPlayed() {
        Match match = createMatch();
        assertEquals(Score.LOVE, match.getScore(PlayerSide.FIRST));
        assertEquals(Score.LOVE, match.getScore(PlayerSide.SECOND));
    }

    @Test
    void getGamesReturnsZeroWhenNoPointsPlayed() {
        Match match = createMatch();
        assertEquals(0, match.getGames(PlayerSide.FIRST));
        assertEquals(0, match.getGames(PlayerSide.SECOND));
    }

    @Test
    void getScoreReturnsLoveBetweenSets() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);
        assertEquals(Score.LOVE, match.getScore(PlayerSide.FIRST));
        assertEquals(Score.LOVE, match.getScore(PlayerSide.SECOND));
    }

    @Test
    void getGamesReturnsZeroBetweenSets() {
        Match match = createMatch();
        winSet(match, PlayerSide.FIRST);
        assertEquals(0, match.getGames(PlayerSide.FIRST));
        assertEquals(0, match.getGames(PlayerSide.SECOND));
    }

    /* ---------- ПРОВЕРКА ИМЁН ИГРОКОВ ---------- */
    @Test
    void matchWithSamePlayerShouldNotBeCreated() {
        Player same = new Player("Same");
        assertThrows(IllegalArgumentException.class, () -> new Match(same, same));
    }
}
