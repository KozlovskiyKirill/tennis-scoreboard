package com.tennis.scoreboard.Model;

import com.tennis.scoreboard.Enums.PlayerSide;
import com.tennis.scoreboard.Enums.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {


    /* ПРОВЕРКА ДОБАВЛЕНИЯ ОЧКОВ (ПЕРВЫЙ И ВТОРОЙ ИГРОКИ) */
    // первый игрок
    @Test
    void firstPointShouldBeFifteen() {
        Game game = new Game();
        game.addPoints(PlayerSide.FIRST);
        assertEquals(Score.FIFTEEN, game.getFirstPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void secondPointShouldBeThirty(){
        Game game = new Game();
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        assertEquals(Score.THIRTY,game.getFirstPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void thirdPointShouldBeForty(){
        Game game = new Game();
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        assertEquals(Score.FORTY,game.getFirstPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void forthPointShouldBeStillForty(){
        Game game = new Game();
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        assertEquals(Score.FORTY,game.getFirstPlayer());
        assertEquals(PlayerSide.FIRST,game.getWinner());
    }

    // второй игрок
    @Test
    void firstPointShouldBeFifteen_2() {
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        assertEquals(Score.FIFTEEN, game.getSecondPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void secondPointShouldBeThirty_2(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertEquals(Score.THIRTY,game.getSecondPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void thirdPointShouldBeForty_2(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertEquals(Score.FORTY,game.getSecondPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void forthPointShouldBeStillForty_2(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertEquals(Score.FORTY,game.getSecondPlayer());
        assertEquals(PlayerSide.SECOND,game.getWinner());
    }

    /*ОПРЕДЕЛЕНИЕ ПОБЕДИТЕЛЯ (ЯВНАЯ ПОБЕДА)*/

    @Test
    void Forty_Plus_Love(){
        Game game = new Game();
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST,game.getWinner());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.LOVE, game.getSecondPlayer());
    }

    @Test
    void Love_Forty_Plus(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND,game.getWinner());
        assertEquals(Score.LOVE, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
    }

    @Test
    void Forty_Plus_Fifteen(){
        Game game = new Game();
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST,game.getWinner());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.FIFTEEN, game.getSecondPlayer());
    }

    @Test
    void Fifteen_Forty_Plus(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND,game.getWinner());
        assertEquals(Score.FIFTEEN, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
    }
    @Test
    void Forty_Plus_Thirty(){
        Game game = new Game();
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST,game.getWinner());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.THIRTY, game.getSecondPlayer());
    }

    @Test
    void Thirty_Forty_Plus(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND,game.getWinner());
        assertEquals(Score.THIRTY, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
    }

    /*НИЧЬЯ (ИГРА НЕ ЗАКАНЧИВАЕТСЯ)*/

    void Forty_Forty(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        assertFalse(game.isFinished());
        assertNull(game.getWinner());
    }

    /* ПОБЕДА ПОСЛЕ НИЧЬИ*/
    void AD_PLUS_Forty(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST,game.getWinner());
        assertEquals(Score.AD, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
    }

    void Forty_Forty_Plus(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);
        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND,game.getWinner());
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.AD, game.getSecondPlayer());
    }

    /*ПОСЛЕ 40 ОБА ИГРОКА НАБРАЛИ AD, ИГРА НЕ ЗАКАНЧИВАЕТСЯ*/

    void AD_AD(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        assertFalse(game.isFinished());
        assertNull(game.getWinner());
    }

    /*МАТЧ ЗАТЯНУЛСЯ ПОСЛЕ 40, 40 НО ПОБЕДИТЕЛЬ ОПРЕДЕН*/
    void First_wins(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);

        assertFalse(game.isFinished());

        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.FIRST);

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.FIRST,game.getWinner());
        assertEquals(Score.AD, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());

    }

    void Second_wins(){
        Game game = new Game();
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.FIRST);

        assertFalse(game.isFinished());

        game.addPoints(PlayerSide.FIRST);
        game.addPoints(PlayerSide.SECOND);
        assertEquals(Score.FORTY, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());
        game.addPoints(PlayerSide.SECOND);
        game.addPoints(PlayerSide.SECOND);

        assertTrue(game.isFinished());
        assertEquals(PlayerSide.SECOND,game.getWinner());
        assertEquals(Score.AD, game.getFirstPlayer());
        assertEquals(Score.FORTY, game.getSecondPlayer());

    }

    /*TIE-BREAK*/
    @Test
    void firstWins(){
        Game game = new Game();
        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.FIRST);

        assertTrue(game.isTieBreakFinished());
        assertEquals(7,game.getFirstPlayerTieBreakPoints());
        assertEquals(5,game.getSecondPlayerTieBreakPoints());
    }

    @Test
    void secondWins(){
        Game game = new Game();
        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.SECOND);
        game.addTieBreakPoints(PlayerSide.SECOND);

        assertTrue(game.isTieBreakFinished());
        assertEquals(5,game.getFirstPlayerTieBreakPoints());
        assertEquals(7,game.getSecondPlayerTieBreakPoints());
    }

    /*РАВНЫЙ СЧЕТ ПОСЛЕ 7, TIE-BREAK НЕ ЗАКАНЧИВАЕТСЯ*/
    @Test
    void DeuceInTieBreak(){
        Game game = new Game();
        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        assertFalse(game.isTieBreakFinished());
        assertEquals(8,game.getFirstPlayerTieBreakPoints());
        assertEquals(8,game.getSecondPlayerTieBreakPoints());
        assertNull(game.getWinner());
    }

    /*ИГРА ЗАТЯГИВАЕТСЯ, НО ЗАКАНЧИВАЕТСЯ ПОБЕДОЙ ОДНОГО ИЗ ИГРОКОВ*/
    @Test
    void FirstWinsInTieBreak(){
        Game game = new Game();
        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.FIRST);

        assertTrue(game.isTieBreakFinished());
        assertEquals(10,game.getFirstPlayerTieBreakPoints());
        assertEquals(8,game.getSecondPlayerTieBreakPoints());
    }

    @Test
    void SecondWinsInTieBreak(){
        Game game = new Game();
        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.FIRST);
        game.addTieBreakPoints(PlayerSide.SECOND);

        game.addTieBreakPoints(PlayerSide.SECOND);
        game.addTieBreakPoints(PlayerSide.SECOND);

        assertTrue(game.isTieBreakFinished());
        assertEquals(8,game.getFirstPlayerTieBreakPoints());
        assertEquals(10,game.getSecondPlayerTieBreakPoints());
    }
}

