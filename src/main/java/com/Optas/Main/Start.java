package com.Optas.Main;

import com.Optas.Main.Models.WordCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class Start {

    public static void main(String[] args) {

        SpringApplication.run(Start.class, args);
    }
    @PostConstruct
    public void init() throws IOException {

        WordCollection.LoadWords();
    }
}
