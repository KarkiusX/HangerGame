package com.Optas.Main.Models;

import java.util.*;


public class Instance {
    private static Map<String, Game> games = new HashMap<>();
   // private static List<Player> playerList = new ArrayList<>();
    public static String StartGame()
    {
        Player player = new Player();
        Game game = new Game(player);
        String uniqueGameId = game.GetGameId();
        games.put(uniqueGameId, game);
        return uniqueGameId;
    }
    public static Game GetGame(String gameId)
    {
        return games.get(gameId);
    }
    public static void UpdateGame(String gameId, Game game)
    {
        games.put(gameId, game);
    }
}
