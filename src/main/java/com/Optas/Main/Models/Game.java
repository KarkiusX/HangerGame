package com.Optas.Main.Models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Game {

    enum GameStates{
        Started, Lost, Won
    }

    private final int MAX_GUESSES = 10;

    private boolean[] revealedLetters;

    private HangerInfo hangerInfo;

    private Player playingPlayer;

    private GameStates gameStates;

    private int missedGuests;


    public Game(Player player)
    {
        playingPlayer = player;

        hangerInfo = WordCollection.SelectRandomHangerInfo();

        revealedLetters = new boolean[hangerInfo.word.length];

        gameStates = GameStates.Started;

    }
    public boolean Guessed(char a)
    {
        boolean guess = false;
        for(int i = 0; i < hangerInfo.word.length; i++)
        {
            if(hangerInfo.word[i] == a)
            {
                guess = true;

                revealedLetters[i] = true;
            }
        }
        if(!guess)
            missedGuests++;

        tryFinishGame();
        return guess;
    }
    public void tryFinishGame()
    {
        if(missedGuests != MAX_GUESSES)
        {
            gameStates = GameStates.Lost;
            return;
        }

        for(int i = 0; i < revealedLetters.length; i++)
        {
            if(!revealedLetters[i])
                return;
        }
        gameStates = GameStates.Won;
    }
    public String GetGameInfo() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("Word", FormatCurrentProgress());
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
            if(revealedLetters[i])
                currentRevealedWord += revealedLetters[i];
            else
                currentRevealedWord += "-";
        }
        return currentRevealedWord;
    }
}
