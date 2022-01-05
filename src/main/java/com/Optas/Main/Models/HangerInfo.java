package com.Optas.Main.Models;

public class HangerInfo {

    public char[] word;
    public String description;

    public HangerInfo(String word, String description)
    {
        this.word = word.toCharArray();
        this.description = description;
    }
}
