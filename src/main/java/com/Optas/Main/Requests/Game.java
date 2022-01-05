package com.Optas.Main.Requests;

import com.Optas.Main.Models.Instance;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class Game {

    @GetMapping("/game")
    public ResponseEntity StartGame(HttpServletResponse response) throws JsonProcessingException {
        String gameId = Instance.StartGame();

        Cookie cookie = new Cookie("gameId", gameId);

        cookie.setMaxAge(10000);
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        cookie.setPath("/");

        response.addCookie(cookie);

        String gameInfo = Instance.GetGame(gameId).GetGameInfo();

        return ResponseEntity.ok(gameInfo);
    }
    @PostMapping("/game")
    public String PlayGame(@RequestBody String word) throws JsonProcessingException {

        return null;
    }
}
