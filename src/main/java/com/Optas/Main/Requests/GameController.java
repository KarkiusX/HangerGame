package com.Optas.Main.Requests;

import com.Optas.Main.Models.Game;
import com.Optas.Main.Models.Instance;
import com.Optas.Main.Models.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class GameController {

    @GetMapping(value = "/game", produces = "application/json")
    public ResponseEntity StartGame() throws JsonProcessingException {
        String gameId = Instance.StartGame();

        String gameInfo = Instance.GetGame(gameId).GetGameInfoJson(true);

        if(gameInfo == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(gameInfo);
    }
    @PutMapping(value = "/game", produces = "application/json")
    public ResponseEntity PlayGame(@RequestBody Player player) throws JsonProcessingException
    {
        if(player.getGuessingLetter().length() < 1)
            return ResponseEntity.notFound().build();

        String gameid = player.getGameId();

        String word = player.getGuessingLetter().toLowerCase();

        Game gameInfo = Instance.GetGame(gameid);

        if(gameInfo == null)
            return ResponseEntity.notFound().build();

        gameInfo.Guessed(word.charAt(0));

        String gameData = gameInfo.GetGameInfoJson(false);

        if(gameInfo.GameFinished())
        {
            Instance.RemoveGame(gameid);
        }
        return ResponseEntity.ok(gameData);
    }
    @PutMapping("/disconnect")
    public ResponseEntity DisconnectFromGame(@RequestBody Player player){
        Instance.RemoveGame(player.getGameId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/connect")
    public ResponseEntity ConnectToGame()
    {
        return ResponseEntity.ok().build();
    }
}
