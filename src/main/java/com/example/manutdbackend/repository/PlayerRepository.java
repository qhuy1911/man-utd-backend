package com.example.manutdbackend.repository;

import com.example.manutdbackend.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
