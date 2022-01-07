package com.Optas.Main.Models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;


public class WordCollection {
    private static List<HangerInfo> wordsWithDescription = new ArrayList<>();

    private static final String wordsFileName = "Words.txt";

    public static void AddWord(String word, String description)
    {
        HangerInfo hangerInfo = new HangerInfo(word, description);
        wordsWithDescription.add(hangerInfo);
    }

    public static HangerInfo SelectRandomHangerInfo()
    {
        Random random = new Random();
        int wordsSize = wordsWithDescription.size();
        if(wordsSize < 1)
        {
            AddWord("Taip", "Geras Žaidimas?");
            wordsSize += 1;
        }
        int wordIndex = random.nextInt(wordsSize);
        return wordsWithDescription.get(wordIndex);
    }
    public static void LoadWords() throws IOException {
        FileInputStream readWords = new FileInputStream(wordsFileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(readWords, "UTF-8"));

        bufferedReader.lines().forEach(line -> {
            String[] words = line.split(":");

            if(words[0].trim().length() > Game.MAX_GUESSES)
                return;

            AddWord(words[0].trim().toLowerCase(), words[1].trim().toLowerCase());
        });
        bufferedReader.close();
    }
}