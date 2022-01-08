package com.Optas.Main.Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class GameTest {


    Game game;

    @BeforeEach
    void setUp() {
        game = new Game(new Player("234"));
        HangerInfo hangerInfo =  new HangerInfo("Tankas", "Yra kare");
        ReflectionTestUtils.setField(game, "hangerInfo", hangerInfo);
        ReflectionTestUtils.setField(game, "revealedLetters", new char[hangerInfo.getWord().length]);
    }

    @Test
    void testGameFinished() {
        ReflectionTestUtils.setField(game, "gameStates", Game.GameStates.Won);
        boolean result = game.GameFinished();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGuessed() {
        boolean result = game.Guessed('a');
        Assertions.assertEquals(true, result);
    }
}
