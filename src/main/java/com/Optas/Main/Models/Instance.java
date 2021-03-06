package com.Optas.Main.Models;

import java.util.*;


public class Instance {
    private static Map<String, Game> games = new HashMap<>();
    public static String StartGame()
    {
        String uid = UUID.randomUUID().toString();
        Player player = new Player(uid);
        Game game = new Game(player);
        games.put(uid, game);
        return uid;
    }
    public static Game GetGame(String gameId)
    {
        if(!games.containsKey(gameId))
            return null;

        return games.get(gameId);
    }
    public static boolean RemoveGame(String gameId) {
        if(!games.containsKey(gameId))
            return false;

        games.remove(gameId);

        return true;
    }
}
