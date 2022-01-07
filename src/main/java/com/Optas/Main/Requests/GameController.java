package com.Optas.Main.Requests;

import com.Optas.Main.Models.Game;
import com.Optas.Main.Models.Instance;
import com.Optas.Main.Models.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class GameController {

    @PostMapping("/game")
    public ResponseEntity StartGame(HttpServletResponse response) throws JsonProcessingException {
        String gameId = Instance.StartGame();

        String gameInfo = Instance.GetGame(gameId).GetGameInfo(true);

        if(gameInfo == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(gameInfo);
    }
    @PutMapping("/game")
    public ResponseEntity PlayGame(@RequestBody Player player, HttpServletRequest request) throws JsonProcessingException
    {
        if(player.getGuessingLetter().length() < 1)
            return ResponseEntity.notFound().build();

        String gameid = player.getGameId();

        String word = player.getGuessingLetter().toLowerCase();

        Game gameInfo = Instance.GetGame(gameid);

        if(gameInfo == null)
            return ResponseEntity.notFound().build();

        gameInfo.Guessed(word.charAt(0));

        String gameData = gameInfo.GetGameInfo(false);

        if(gameInfo.GameFinished())
        {
            Instance.RemoveGame(gameid);
        }
        return ResponseEntity.ok(gameData);
    }
    @PutMapping("/disconnect")
    public ResponseEntity DisconnectFromGame(@RequestBody Player player){
        System.out.println("Hello");
        Instance.RemoveGame(player.getGameId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/connect")
    public ResponseEntity ConnectToGame(HttpServletRequest request){
        return null;
    }
}
