package com.Optas.Main.Requests;

import com.Optas.Main.Models.Instance;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000/", allowCredentials = "true")
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
    @PutMapping("/game/{word}")
    public ResponseEntity PlayGame(@PathVariable String word, HttpServletRequest request) throws JsonProcessingException {
        Optional<Cookie> gameCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("gameId")).findFirst();
        if(gameCookie.isPresent())
        {
            if(word.length() == 0)
                return null;

            String gameid = gameCookie.get().getValue();
            com.Optas.Main.Models.Game gameInfo = Instance.GetGame(gameid);

            gameInfo.Guessed(word.charAt(0));

            return ResponseEntity.ok(gameInfo.GetGameInfo());
        }
        return null;
    }
}
