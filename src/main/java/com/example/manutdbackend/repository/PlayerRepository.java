package com.example.manutdbackend.repository;

import com.example.manutdbackend.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(value = "SELECT * FROM manutd.players WHERE position = 'Goalkeeper'", nativeQuery = true)
    List<Player> findGoalkeeperPlayers();

    @Query(value = "SELECT * FROM manutd.players WHERE position = 'Defender'", nativeQuery = true)
    List<Player> findDefenderPlayers();

    @Query(value = "SELECT * FROM manutd.players WHERE position = 'Midfielder'", nativeQuery = true)
    List<Player> findMidfielderPlayers();

    @Query(value = "SELECT * FROM manutd.players WHERE position = 'Forward'", nativeQuery = true)
    List<Player> findForwardPlayers();
}
