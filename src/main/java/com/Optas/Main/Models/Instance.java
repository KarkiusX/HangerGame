package com.Optas.Main.Models;

import java.util.*;


public class Instance {
    private static Map<String, Game> games = new HashMap<>();
    private static List<Player> playerList = new ArrayList<>();
    public static String StartGame()
    {
        Player player = new Player();
        String uniqueGameId = UUID.randomUUID().toString();
        Game game = new Game(player);
        games.put(uniqueGameId, game);
        return uniqueGameId;
    }
    public static Game GetGame(String gameId)
    {
        return games.get(gameId);
    }
}
