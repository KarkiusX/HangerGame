package com.Optas.Main.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HangerInfo {

    private char[] word;
    private String description;

    public HangerInfo(String word, String description)
    {
        this.word = word.toCharArray();
        this.description = description;
    }
}
