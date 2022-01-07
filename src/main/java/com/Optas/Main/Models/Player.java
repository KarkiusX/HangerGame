package com.Optas.Main.Models;

public class Player {

    private String gameId;

    private String guessingLetter;

    public Player(){};

    public Player(String gameId)
    {
        this.gameId = gameId;
    }

    public String getGuessingLetter() {
        return guessingLetter;
    }

    public void setGuessingLetter(String guessingLetter) {
        this.guessingLetter = guessingLetter;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
