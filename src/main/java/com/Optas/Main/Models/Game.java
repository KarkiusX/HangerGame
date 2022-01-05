package com.Optas.Main.Models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

public class Game {

    enum GameStates{
        Started, Lost, Won
    }

    private final int MAX_GUESSES = 10;

    private char[] revealedLetters;

    private HangerInfo hangerInfo;

    private Player playingPlayer;

    private GameStates gameStates;

    private int guesses;

    private String gameId;


    public Game(Player player)
    {
        playingPlayer = player;

        hangerInfo = WordCollection.SelectRandomHangerInfo();

        revealedLetters = new char[hangerInfo.word.length];

        gameStates = GameStates.Started;

        gameId = UUID.randomUUID().toString();

    }
    public String GetGameId()
    {
        return gameId;
    }
    public boolean Guessed(char a)
    {
        boolean guess = false;
        for(int i = 0; i < hangerInfo.word.length; i++)
        {
            if(hangerInfo.word[i] == a)
            {
                guess = true;

                revealedLetters[i] = a;
            }
        }
        guesses++;

        tryFinishGame();
        return guess;
    }
    public void tryFinishGame()
    {
        if(guesses == MAX_GUESSES)
        {
            gameStates = GameStates.Lost;
            Instance.UpdateGame( gameId, this);
            return;
        }

        for(int i = 0; i < revealedLetters.length; i++)
        {
            if(revealedLetters[i] == 0)
                return;
        }
        gameStates = GameStates.Won;
        Instance.UpdateGame( gameId, this);
    }
    public String GetGameInfo() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("Word", FormatCurrentProgress());
        objectNode.put("Guesses", guesses);
        objectNode.put("Description", hangerInfo.description);
        objectNode.put("GameState", gameStates.toString());

        String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);

        return jsonData;
    }
    private String FormatCurrentProgress()
    {
        String currentRevealedWord = "";


        for(int i =0; i < hangerInfo.word.length; i++)
        {
            if(revealedLetters[i] != 0)
                currentRevealedWord += revealedLetters[i];
            else
                currentRevealedWord += "-";
        }
        return currentRevealedWord;
    }
}
