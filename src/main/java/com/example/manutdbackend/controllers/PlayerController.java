package com.example.manutdbackend.controllers;

import com.example.manutdbackend.exception.ResourceNotFoundException;
import com.example.manutdbackend.models.Player;
import com.example.manutdbackend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        if (players.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found PLAYER with id = " + id));
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @GetMapping("/players/goalkeeper")
    public ResponseEntity<List<Player>> getGoalkeepersPlayers() {
        List<Player> players = playerRepository.findGoalkeeperPlayers();
        if (players.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/players/defender")
    public ResponseEntity<List<Player>> getDefenderPlayers() {
        List<Player> players = playerRepository.findDefenderPlayers();
        if (players.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/players/midfielder")
    public ResponseEntity<List<Player>> getMidfielderPlayers() {
        List<Player> players = playerRepository.findMidfielderPlayers();
        if (players.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/players/forward")
    public ResponseEntity<List<Player>> getForwardPlayers() {
        List<Player> players = playerRepository.findForwardPlayers();
        if (players.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}
