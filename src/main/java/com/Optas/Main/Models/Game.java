package com.Optas.Main.Models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

@Getter
public class Game {

    enum GameStates{
        Started, Lost, Won
    }
    public static final int MAX_GUESSES = 10;

    private char[] revealedLetters;

    private HangerInfo hangerInfo;

    private Player playingPlayer;

    private GameStates gameStates;

    private int guesses;
    private int incorrectGuesses;

    private String lettersGuessed;


    public Game(Player player)
    {
        playingPlayer = player;

        hangerInfo = WordCollection.SelectRandomHangerInfo();

        revealedLetters = new char[hangerInfo.getWord().length];

        gameStates = GameStates.Started;

        lettersGuessed = "";

    }

    public boolean GameFinished()
    {
        if(gameStates == GameStates.Lost || gameStates == GameStates.Won)
            return true;

        return false;
    }
    public boolean Guessed(char a)
    {
        boolean guess = false;
        lettersGuessed += a + " ";
        for(int i = 0; i < hangerInfo.getWord().length; i++)
        {
            if(hangerInfo.getWord()[i] == a && revealedLetters[i] == 0)
            {
                guess = true;

                revealedLetters[i] = a;
            }
        }
        guesses++;

        if(!guess)
            incorrectGuesses = guesses;

        tryFinishGame();

        return guess;
    }
    private void tryFinishGame()
    {
        boolean wonGame = true;
        for(int i = 0; i < revealedLetters.length; i++)
        {
            if(revealedLetters[i] == 0)
            {
                wonGame = false;
                break;
            }
        }
        if(guesses == MAX_GUESSES && !wonGame)
        {
            gameStates = GameStates.Lost;
            return;
        }
        if(wonGame)
            gameStates = GameStates.Won;
    }
    public String GetGameInfoJson(boolean includeId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();

        if(includeId)
            objectNode.put("gameId", playingPlayer.getGameId());

        objectNode.put("Word", FormatCurrentProgress());
        objectNode.put("Guesses", guesses);
        objectNode.put("ig", incorrectGuesses);
        objectNode.put("Description", hangerInfo.getDescription());
        objectNode.put("LettersGuessed", lettersGuessed);
        objectNode.put("GameState", gameStates.toString());

        if(gameStates == GameStates.Lost)
        {
            objectNode.put("cw", String.valueOf(hangerInfo.getWord()));
        }

        String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);

        return jsonData;
    }
    private String FormatCurrentProgress()
    {
        String currentRevealedWord = "";


        for(int i =0; i < hangerInfo.getWord().length; i++)
        {
            if(revealedLetters[i] != 0)
                currentRevealedWord += revealedLetters[i];
            else
                currentRevealedWord += "-";
        }
        return currentRevealedWord;
    }
}
