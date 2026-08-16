package com.tennis.scoreboard.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    /* ---------- ВАЛИДНОЕ ИМЯ ---------- */
    @Test
    void validNameIsStored() {
        Player player = new Player("Nadal");
        assertEquals("Nadal", player.name());
    }

    /* ---------- НЕВАЛИДНЫЕ ИМЕНА ---------- */
    @Test
    void nullNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Player(null));
    }

    @Test
    void blankNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Player(""));
        assertThrows(IllegalArgumentException.class, () -> new Player("   "));
    }

    @Test
    void tooLongNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Player("12345678901"));
    }
}
