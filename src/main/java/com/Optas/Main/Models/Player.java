package com.Optas.Main.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Player {

    private String gameId;

    private String guessingLetter;

    public Player(String gameId)
    {
        this.gameId = gameId;
    }

}
