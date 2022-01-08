package com.Optas.Main.Requests;

import com.Optas.Main.Models.Instance;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testStartGame() throws Exception {
        this.mockMvc.perform(get("/game")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testPlayGame() throws Exception {

        String id = Instance.StartGame();

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("gameId", id);
        objectNode.put("guessingLetter", "a");

        String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);

        mockMvc.perform(put("/game").contentType(MediaType.APPLICATION_JSON).content(jsonData)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDisconnectFromGame() throws Exception {
        String id = Instance.StartGame();

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("gameId", id);

        String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);

        mockMvc.perform(put("/disconnect").contentType(MediaType.APPLICATION_JSON).content(jsonData)).andExpect(status().isOk());
    }

    @Test
    void testConnectToGame() throws Exception {
        mockMvc.perform(get("/connect")).andExpect(status().isOk());
    }
}
