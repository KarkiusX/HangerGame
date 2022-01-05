package com.Optas.Main.Models;

import java.util.*;


public class WordCollection {
    private static List<HangerInfo> wordsWithDescription = new ArrayList<>();

    public static void AddWord(String word, String description)
    {
        wordsWithDescription.add(new HangerInfo(word, description));
    }

    public static HangerInfo SelectRandomHangerInfo()
    {
        Random random = new Random();
        int wordsSize = wordsWithDescription.size();
        if(wordsSize < 1)
        {
            AddWord("Tusčia", "Kaip pamatai puodelį ir ten nieko nėra ištari");
            wordsSize += 1;
        }
        int wordIndex = random.nextInt(wordsSize);
        return wordsWithDescription.get(wordIndex);
    }
}
